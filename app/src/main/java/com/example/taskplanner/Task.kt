package com.example.taskplanner

data class Task(
    val id: Int,
    val title: String,
    val description: String="",
    var isDone: Boolean = false   // <-- Add this
)

