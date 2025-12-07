package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.entities.Note
import com.example.taskplanner.data.entities.Task
import com.example.taskplanner.data.repository.NoteRepository
import com.example.taskplanner.data.repository.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.collections.emptyList

class HomeViewModel(
    private val taskRepository: TaskRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {

    val allTasks2 = taskRepository.allTasks //

    @RequiresApi(Build.VERSION_CODES.O)
    val overdueTasks: StateFlow<List<Task>> = allTasks2
        .map { list -> list.filter { it.isOverdue() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    // value for overdue tasks
    @RequiresApi(Build.VERSION_CODES.O)
    val upcomingTasks: StateFlow<List<Task>> = allTasks2
        .map { list -> list.filter { it.upcoming() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
// checks to see if the task is overdue
    @RequiresApi(Build.VERSION_CODES.O)
    private fun Task.isOverdue(): Boolean {
        return this.date < LocalDate.now().toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun Task.upcoming(): Boolean {
        return this.date > LocalDate.now().toString()
    }

    // TASK SECTION
    val allTasks: StateFlow<List<Task>> =
        taskRepository.allTasks.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun tasksForDate(date: String) = taskRepository.getTasksForDate(date)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTask(title: String, description: String, date: String, photoPath: String?) {
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description,
                date = date,
                photoPath = photoPath
            )
            taskRepository.insert(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { taskRepository.update(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { taskRepository.delete(task) }
    }

    // NOTE SECTION
    val allNotes: StateFlow<List<Note>> =
        noteRepository.allNotes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addNote(note: Note) {
        viewModelScope.launch { noteRepository.insert(note) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch { noteRepository.update(note) }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { noteRepository.delete(note) }
    }
}
