package com.example.taskplanner.data.repository

import com.example.taskplanner.data.dao.NoteDao
import com.example.taskplanner.data.entities.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    // Retrieve all notes whilst using flow
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
    // insert note function
    suspend fun insert(note: Note) = noteDao.insert(note)
// update notes
    suspend fun update(note: Note) = noteDao.update(note)
// delete notes
    suspend fun delete(note: Note) = noteDao.delete(note)
}