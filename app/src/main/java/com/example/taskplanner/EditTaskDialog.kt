@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import com.example.taskplanner.data.entities.Task
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    // we assume task.date is a String in ISO format "yyyy-M-d" or "yyyy-MM-dd"
    var dateString by remember { mutableStateOf(task.date) }

    // Date picker state
    val datePickerState = rememberDatePickerState()
    var showPicker by remember { mutableStateOf(false) }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val picked = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                        dateString = picked.toString()
                    }
                    showPicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val updated = task.copy(
                    title = title,
                    description = description,
                    date = dateString
                )
                onSave(updated)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("Edit task") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                // date field (read-only clickable to open date picker)
                OutlinedTextField(
                    value = dateString,
                    onValueChange = { /* read-only */ },
                    label = { Text("Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // optional: set initial selectedDateMillis if you want
                            // parse dateString -> millis
                            showPicker = true
                        },
                    readOnly = true
                )

                Spacer(Modifier.height(12.dp))

                // Delete button as a destructive action
                TextButton(
                    onClick = { onDelete(task) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete task", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}
