package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodaysTasksScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    // Collect today's tasks from ViewModel
    val tasks by viewModel.todayTasks.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Today's Tasks") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(tasks) { task ->
                ExpandableTaskItem(
                    task = task,
                    onToggleDone = { viewModel.toggleTaskDone(task.id) }
                )
            }
        }
    }
}

@Composable
fun ExpandableTaskItem(
    task: Task,
    onToggleDone: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Task title and checkbox
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)

                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { onToggleDone() }
                )
            }

            // Task details (description) shown when expanded
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
