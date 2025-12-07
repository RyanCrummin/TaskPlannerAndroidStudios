package com.example.taskplanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
)
/*
This is the entity class for the Notes

This is just initialising the data structure for what data will be passed to the db
 */
