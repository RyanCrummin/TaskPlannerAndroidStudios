package com.example.taskplanner.data.repository

import com.example.taskplanner.data.dao.TaskDao
import com.example.taskplanner.data.entities.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    // Get all tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    // Insert a new task
    suspend fun insert(task: Task) {
        taskDao.insert(task)
    }
    // Update a task
    suspend fun update(task: Task) {
        taskDao.update(task)
    }
    // Delete a task
    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }
    suspend fun getTaskById(id: Int): Task? = taskDao.getTaskById(id)
    fun getTasksForDate(date: String) = taskDao.getTasksForDate(date)
}
