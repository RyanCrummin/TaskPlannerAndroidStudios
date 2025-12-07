package com.example.taskplanner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskplanner.data.entities.Task
import java.time.LocalDate

@Composable

fun AddTaskScreen(
    viewModel: HomeViewModel,
    onSaveTask: () -> Unit,
    onCancel: () -> Unit,
    onAddTask: (String, String) -> Unit
)
{
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Button(onClick = {
            onAddTask(title, description) // <-- call the helper here
            onSaveTask()
        }) {
            Text("Save Task")
        }
        Button(onClick = onCancel) {
            Text("Cancel")
        }
    }

}

