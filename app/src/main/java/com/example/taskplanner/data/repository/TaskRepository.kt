package com.example.taskplanner.data.repository

import com.example.taskplanner.data.dao.TaskDao
import com.example.taskplanner.data.entity.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    // Get all tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    // Insert a new task
    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    // Update a task
    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    // Delete a task
    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}
