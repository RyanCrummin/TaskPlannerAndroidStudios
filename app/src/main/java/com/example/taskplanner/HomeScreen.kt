package com.example.taskplanner

import androidx.compose.foundation.background
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

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTodayClick: () -> Unit = {},
    onUpcomingClick: () -> Unit = {},
    onAddTaskClick: () -> Unit = {},
    onAddNoteClick: () -> Unit = {}
) {
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

        NotesSection(onAddNoteClick = onAddNoteClick)
        OverdueTasksSection()
        CalendarSection()
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
private fun NotesSection(onAddNoteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFAB82FF))
            .padding(16.dp)
    ) {
        Text(text = "Notes", color = Color.White, fontSize = 18.sp)
        TextButton(
            onClick = onAddNoteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) { Text(text = "Add Note", color = Color.White) }
    }
}

@Composable
private fun OverdueTasksSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFFFF1493)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Over-Due Tasks", color = Color.White, fontSize = 18.sp)
    }
}

@Composable
private fun CalendarSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF0000FF)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Calendar", color = Color.White, fontSize = 18.sp)
    }
}
