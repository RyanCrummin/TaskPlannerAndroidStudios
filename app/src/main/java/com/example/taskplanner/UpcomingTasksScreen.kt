package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingTasksScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    // Collect upcoming tasks from ViewModel
    val tasks by viewModel.todayTasks.collectAsState(initial = emptyList()) // You can filter for upcoming if you add dates

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upcoming Tasks") },
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
                // Reuse the same ExpandableTaskItem from TodaysTasksScreen
                ExpandableTaskItem(
                    task = task,
                    onToggleDone = { viewModel.toggleTaskDone(task.id) }
                )
            }
        }
    }
}
