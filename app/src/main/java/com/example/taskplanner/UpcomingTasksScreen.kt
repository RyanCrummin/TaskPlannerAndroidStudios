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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskplanner.data.entities.Task

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpcomingTasksScreen(viewModel: HomeViewModel, onBack: () -> Unit) {
    val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    val tasks by viewModel.tasksForDate(today).collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Upcoming Tasks", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckChange = { updatedTask -> viewModel.updateTask(updatedTask) },
                    onDeleteClick = { viewModel.deleteTask(task) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Button(
            onClick = {
                val newTask = Task(
                    title = "New Task",
                    description = "",
                    date = today,
                    isDone = false
                )
                viewModel.addTask(newTask)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
    }
}


