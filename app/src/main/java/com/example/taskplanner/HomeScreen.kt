package com.example.taskplanner

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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopButtonsRow(
            onTodayClick = onTodayClick,
            onUpcomingClick = onUpcomingClick,
            onAddTaskClick = onAddTaskClick
        )

        NotesSection(viewModel = viewModel, onAddNoteClick = onAddNoteClick)
        OverdueTasksSection(onOverdueClick = onOverdueClick)
        CalendarSection(onCalendarClick = onCalendarClick)
    }
}


@Composable
private fun TopButtonsRow(
    onTodayClick: () -> Unit,
    onUpcomingClick: () -> Unit,
    onAddTaskClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onTodayClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.weight(1f)
        ) { Text(text = "Today's Tasks", color = Color.White) }

        Button(
            onClick = onUpcomingClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB)),
            modifier = Modifier.weight(1f)
        ) { Text(text = "Upcoming Tasks", color = Color.Black) }

        Button(
            onClick = onAddTaskClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF00)),
            modifier = Modifier.weight(1f)
        ) { Text(text = "Add Tasks", color = Color.Black) }
    }
}

@Composable
private fun NotesSection(
    viewModel: HomeViewModel,
    onAddNoteClick: () -> Unit
) {
    // Collect notes from ViewModel
    val notes = viewModel.notes.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFAB82FF))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Notes", color = Color.White, fontSize = 18.sp)
            TextButton(onClick = onAddNoteClick) {
                Text(text = "Add Note", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (notes.isEmpty()) {
            Text(text = "No notes yet", color = Color.White)
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                notes.forEach { note ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFBAA0FF))
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                note.title,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                note.content,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun OverdueTasksSection(
    onOverdueClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFFFF1493))
            .clickable { onOverdueClick() }, // placeholder click
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Over-Due Tasks",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun CalendarSection(
    onCalendarClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF0000FF))
            .clickable { onCalendarClick() }, // placeholder click
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Calendar",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

