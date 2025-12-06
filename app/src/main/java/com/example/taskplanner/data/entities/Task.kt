package com.example.taskplanner.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Auto-incremented primary key
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)
