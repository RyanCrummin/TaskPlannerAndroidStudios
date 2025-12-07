package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.taskplanner.data.entities.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodaysTasksScreen(viewModel: HomeViewModel, onBack: () -> Unit) {
    val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    val tasks by viewModel.tasksForDate(today).collectAsState(initial = emptyList())

    var editMode by remember { mutableStateOf(false) }
    var taskBeingEdited by remember { mutableStateOf<Task?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Today's Tasks", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { editMode = !editMode }) {
                Text(if (editMode) "Done" else "Edit")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tasks) { task ->
                var expanded by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = { isChecked ->
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
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (editMode) {
                        IconButton(onClick = { taskBeingEdited = task }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Task")
                        }
                        IconButton(onClick = { viewModel.deleteTask(task) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                        }
                    } else {
                        IconButton(onClick = { viewModel.deleteTask(task) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
                        }
                    }
                }

                Divider()
            }
        }

        // Edit Dialog
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
}





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    task: Task,
    onCheckChange: (Task) -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { isChecked -> onCheckChange(task.copy(isDone = isChecked)) }
            )

            Text(task.title, modifier = Modifier.weight(1f).padding(start = 8.dp))

            IconButton(onClick = onEditClick) { Icon(Icons.Default.Edit, contentDescription = "Edit Task") }
            IconButton(onClick = onDeleteClick) { Icon(Icons.Default.Delete, contentDescription = "Delete Task") }
        }

        if (task.description.isNotBlank()) Text(task.description, modifier = Modifier.padding(start = 32.dp))

        task.photoPath?.let { path ->
            val bitmap = android.graphics.BitmapFactory.decodeFile(path)
            bitmap?.let {
                androidx.compose.foundation.Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Task Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                )
            }
        }

        Divider()
    }
}

@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var date by remember { mutableStateOf(task.date) }
    var photoPath by remember { mutableStateOf(task.photoPath) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date (YYYY-MM-DD)") })
                Spacer(Modifier.height(8.dp))
                photoPath?.let {
                    val bitmap = android.graphics.BitmapFactory.decodeFile(it)
                    bitmap?.let { bmp ->
                        androidx.compose.foundation.Image(
                            bitmap = bmp.asImageBitmap(),
                            contentDescription = "Task Photo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(task.copy(title = title, description = description, date = date, photoPath = photoPath))
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

