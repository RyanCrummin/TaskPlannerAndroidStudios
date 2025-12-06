package com.example.taskplanner

import java.sql.Date
import java.time.LocalDate

data class Task(
    val id: Int,
    val title: String,
    val description: String="",
    val dueDate: LocalDate,
    var isDone: Boolean = false   // <-- Add this
)

