package com.example.taskplanner.data.dao

import androidx.room.*
import com.example.taskplanner.data.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY date")
    fun getAllTasks(): Flow<List<Task>> // gets tasks and orders them by date
    @Query("SELECT * FROM tasks WHERE date = :date")
    fun getTasksForDate(date: String): Flow<List<Task>> // gets tasks where task = to a certain date
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Wherever there is a conflict upon insert, replace it
    suspend fun insert(task: Task) // insert task
    @Update
    suspend fun update(task: Task) // update task
    @Delete
    suspend fun delete(task: Task) //delete task
    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getTaskById(id: Int): Task? // getting the task by ID
}
