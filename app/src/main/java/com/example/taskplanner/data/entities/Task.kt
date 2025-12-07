package com.example.taskplanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-incremented primary key
    val title: String,
    val description: String,
    val date: String,
    val isDone: Boolean = false,
    val photoPath: String? = null
)

/*
This is the entity class for the Tasks

This is just initialising the data structure for what data will be passed to the db
 */
