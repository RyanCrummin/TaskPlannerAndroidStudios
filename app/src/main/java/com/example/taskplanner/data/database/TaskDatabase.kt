package com.example.taskplanner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskplanner.data.dao.NoteDao
import com.example.taskplanner.data.dao.TaskDao
import com.example.taskplanner.data.entities.Task
import com.example.taskplanner.data.entities.Note
@Database(entities = [Task::class, Note::class], version = 2, exportSchema = true) // Uses both Tasks and Notes entities
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao // initialise the TaskDao
    abstract fun noteDao(): NoteDao // initialise the NoteDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // getting the DB
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database" // name of database
                ).build()
                INSTANCE = instance // creates the instance of the database
                instance
            }
        }
    }
}
