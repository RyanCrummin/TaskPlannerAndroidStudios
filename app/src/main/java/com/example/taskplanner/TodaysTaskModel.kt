package com.example.taskplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class TodaysTaskModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    // Only tasks due today
    val todayTasks = tasks
        .map { list ->
            val today = LocalDate.now()
            list.filter { it.dueDate == today }
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
