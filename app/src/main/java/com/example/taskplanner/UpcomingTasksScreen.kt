package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.taskplanner.data.entities.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpcomingTasksScreen(viewModel: HomeViewModel, onBack: () -> Unit) {

    val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    val tasks by viewModel.upcomingTasks.collectAsState(initial = emptyList()) // Ensure you have upcomingTasks in your ViewModel

    var editMode by remember { mutableStateOf(false) }
    var taskBeingEdited by remember { mutableStateOf<Task?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Top row with Back button and Edit toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Upcoming Tasks", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { editMode = !editMode }) {
                Text(if (editMode) "Done" else "Edit")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tasks list
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
                        if (expanded) {

                            // DESCRIPTION
                            if (task.description.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    task.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // PHOTO
                            if (task.photoPath != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Image(
                                    painter = rememberAsyncImagePainter(task.photoPath),
                                    contentDescription = "Task Photo",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                )
                            }
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


        // Edit dialog
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


