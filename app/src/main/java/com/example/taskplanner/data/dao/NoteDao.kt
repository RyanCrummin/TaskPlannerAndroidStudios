package com.example.taskplanner.data.dao

import androidx.room.*
import com.example.taskplanner.data.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Note>> // using flow to get all notes

    @Insert
    suspend fun insert(note: Note) // insert note

    @Update
    suspend fun update(note: Note) // update note

    @Delete
    suspend fun delete(note: Note) //delete note
}