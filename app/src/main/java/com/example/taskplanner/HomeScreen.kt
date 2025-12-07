package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTodayClick: () -> Unit,
    onUpcomingClick: () -> Unit,
    onAddTaskClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Buttons row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Button(onClick = onTodayClick) {
                Text("Today")
            }
            Button(onClick = onUpcomingClick) {
                Text("Upcoming")
            }
            Button(onClick = onAddTaskClick) {
                Text("Add Task")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Overdue Tasks Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Overdue Tasks", style = MaterialTheme.typography.titleMedium)
                val overdueTasks = viewModel.overdueTasks.collectAsState()
                overdueTasks.value.forEach { task ->
                    Text("- ${task.title}")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notes Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("My Notes", style = MaterialTheme.typography.titleMedium)
                    Button(onClick = onAddNoteClick) {
                        Text("Add Note")
                    }
                }

                val notes = viewModel.allNotes.collectAsState()
                notes.value.forEach { note ->
                    Text("- ${note.title}")
                }
            }
        }
    }
}




