package com.example.taskplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.entities.Task
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

import java.util.Calendar

class TodaysTaskModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()
    val today = Calendar.getInstance()
    val day = today.get(Calendar.DAY_OF_MONTH)
    val month = today.get(Calendar.MONTH) + 1 // Months start at 0
    val year = today.get(Calendar.YEAR)
    val dateString = "$year-$month-$day" // Format as you like

    // Only tasks due today
    val todayTasks = tasks
        .map { list ->
            val today = dateString
            list.filter { it.date == today }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addTask(task: Task) {
        _tasks.value = _tasks.value + task
    }

    fun markTaskDone(id: Int, done: Boolean) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(isDone = done) else it
        }
    }
}
