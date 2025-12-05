package com.example.taskplanner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable

@Composable
fun TodaysTasksScreen(
    tasks: List<Task> = emptyList(),
    onTaskClick: (Task) -> Unit = {},
    onBack: () -> Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Today's Tasks", fontSize = 24.sp, color = Color.Black)

        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No tasks for today!", color = Color.Gray)
            }
        } else {
            tasks.forEach { task ->
                TaskItem(task = task, onClick = { onTaskClick(task) })
            }
        }
    }
}
@Composable
fun TaskItem(task: Task, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, fontSize = 18.sp)
            if (task.description.isNotEmpty()) {
                Text(text = task.description, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}
