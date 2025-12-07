package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskplanner.data.entities.Note
import com.example.taskplanner.data.entities.Task
import coil.compose.rememberAsyncImagePainter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTodayClick: () -> Unit,
    onUpcomingClick: () -> Unit,
    onAddTaskClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    // Notes state
    var notesEditMode by remember { mutableStateOf(false) }
    var showNoteEditDialog by remember { mutableStateOf(false) }
    var noteBeingEdited by remember { mutableStateOf<Note?>(null) }

    // Overdue tasks state
    var overdueEditMode by remember { mutableStateOf(false) }
    var taskBeingEdited by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // ---------------- Buttons Row ----------------
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Button(onClick = onTodayClick) { Text("Today") }
            Button(onClick = onUpcomingClick) { Text("Upcoming") }
            Button(onClick = onAddTaskClick) { Text("Add Task") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------------- Overdue Tasks Box ----------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            val overdueTasks by viewModel.overdueTasks.collectAsState(initial = emptyList())

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Overdue Tasks", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = { overdueEditMode = !overdueEditMode }) {
                        Text(if (overdueEditMode) "Done" else "Edit")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                overdueTasks.forEach { task ->
                    var expanded by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { isChecked ->
                                // mirror Today's behavior — just mark done, don't delete
                                viewModel.updateTask(task.copy(isDone = isChecked))
                            }
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(task.title, style = MaterialTheme.typography.bodyLarge)
                            if (expanded && task.description.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    task.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }

                            // show image when expanded (if exists)
                            if (expanded && task.photoPath != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                androidx.compose.foundation.Image(
                                    painter = rememberAsyncImagePainter(task.photoPath),
                                    contentDescription = "Task Photo",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                )
                            }
                        }

                        if (overdueEditMode) {
                            IconButton(onClick = { taskBeingEdited = task }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit Task")
                            }
                        }

                        IconButton(onClick = { viewModel.deleteTask(task) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Task",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Divider(color = Color.Black.copy(alpha = 0.1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------------- Notes Box ----------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val notes by viewModel.allNotes.collectAsState()

                // HEADER ROW
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("My Notes", style = MaterialTheme.typography.titleMedium)
                    Button(onClick = onAddNoteClick) { Text("Add Note") }
                }

                Spacer(modifier = Modifier.height(10.dp))

                notes.forEach { note ->
                    var expanded by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(note.title, style = MaterialTheme.typography.titleMedium)
                            if (expanded && note.content.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    note.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (notesEditMode) {
                            Row {
                                IconButton(onClick = { viewModel.deleteNote(note) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }

                                IconButton(onClick = {
                                    noteBeingEdited = note
                                    showNoteEditDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                            }
                        }
                    }
                    Divider()
                }

                Spacer(modifier = Modifier.height(8.dp))

                // EDIT NOTES BUTTON
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalButton(onClick = { notesEditMode = !notesEditMode }) {
                        Text(if (notesEditMode) "Done" else "Edit Notes")
                    }
                }

                // Edit Note Dialog
                if (showNoteEditDialog && noteBeingEdited != null) {
                    EditNoteDialog(
                        note = noteBeingEdited!!,
                        onDismiss = { showNoteEditDialog = false },
                        onSave = { updatedNote ->
                            viewModel.updateNote(updatedNote)
                            showNoteEditDialog = false
                        }
                    )
                }
            }
        }
    }

    // ---------------- Edit Task Dialog for Overdue ----------------
    if (taskBeingEdited != null) {
        EditTaskDialog(
            task = taskBeingEdited!!,
            onDismiss = { taskBeingEdited = null },
            onSave = { updatedTask ->
                viewModel.updateTask(updatedTask)
                taskBeingEdited = null
            }
        )
    }
}

/** EditNoteDialog kept local to this file for convenience **/
@Composable
fun EditNoteDialog(
    note: Note,
    onDismiss: () -> Unit,
    onSave: (Note) -> Unit
) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Note") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(note.copy(title = title, content = content))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
