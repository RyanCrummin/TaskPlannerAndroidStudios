@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.taskplanner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun AddNotesScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    var noteTitle by remember { mutableStateOf(TextFieldValue("")) }
    var noteContent by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Note") },
                navigationIcon = {} // empty composable instead of null
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Title", style = MaterialTheme.typography.titleMedium)
            TextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Content", style = MaterialTheme.typography.titleMedium)
            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (noteTitle.text.isNotBlank() && noteContent.text.isNotBlank()) {
                        val newNote = Note(
                            id = viewModel.getNextNoteId(),
                            title = noteTitle.text,
                            content = noteContent.text
                        )
                        viewModel.addNote(newNote)
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Note")
            }
        }
    }
}
