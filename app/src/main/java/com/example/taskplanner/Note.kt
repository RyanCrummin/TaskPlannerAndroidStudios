package com.example.taskplanner

data class Note(
    val id: Int, // auto-increment later if using Room
    val title: String,
    val content: String
)