package com.example.taskplanner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun AddTaskScreen(
    viewModel: HomeViewModel,
    onSaveTask: (Task) -> Unit,
    onCancel: () -> Unit
) {
    // Collect current tasks
    val tasks by viewModel.todayTasks.collectAsState()

    // Form state
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(LocalDate.now()) }

    // Generate next task ID
    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Add New Task", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // TODO: Replace with proper date picker
        OutlinedTextField(
            value = dueDate.toString(),
            onValueChange = { /* parse date if needed */ },
            label = { Text("Due Date") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                val newTask = Task(
                    id = nextId,
                    title = title,
                    description = description,
                    dueDate = dueDate
                )
                viewModel.addTask(newTask)
                onSaveTask(newTask)
            }) {
                Text("Save")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}
