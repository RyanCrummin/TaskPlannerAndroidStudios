package com.example.taskplanner.data.dao

import androidx.room.*
import com.example.taskplanner.data.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY date")
    fun getAllTasks(): Flow<List<Task>>
    @Query("SELECT * FROM tasks WHERE date = :date")
    fun getTasksForDate(date: String): Flow<List<Task>>
    @Insert
    suspend fun insert(task: Task)
    @Update
    suspend fun update(task: Task)
    @Delete
    suspend fun delete(task: Task)
    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    suspend fun getTaskById(id: Int): Task?
}
