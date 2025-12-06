package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTodayClick: () -> Unit = {},
    onUpcomingClick: () -> Unit = {},
    onAddTaskClick: () -> Unit = {},
    onAddNoteClick: () -> Unit = {},
    onOverdueClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {}
) {
    val notes by viewModel.notes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        /** --- Top Buttons --- **/
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ElevatedButton(
                onClick = onTodayClick,
                modifier = Modifier.weight(1f)
            ) { Text("Today") }

            ElevatedButton(
                onClick = onUpcomingClick,
                modifier = Modifier.weight(1f)
            ) { Text("Upcoming") }

            FilledTonalButton(
                onClick = onAddTaskClick,
                modifier = Modifier.weight(1f)
            ) { Text("Add Task") }
        }

        /** --- Notes Card --- **/
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Notes",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    TextButton(onClick = onAddNoteClick) {
                        Text("Add")
                    }
                }

                if (notes.isEmpty()) {
                    Text(
                        "No notes yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        notes.forEach { note ->
                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.elevatedCardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        note.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        note.content,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        /** --- Overdue --- **/
        ElevatedCard(
            onClick = onOverdueClick,
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Overdue Tasks", style = MaterialTheme.typography.titleMedium)
            }
        }

        /** --- Calendar --- **/
        ElevatedCard(
            onClick = onCalendarClick,
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Calendar", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}


