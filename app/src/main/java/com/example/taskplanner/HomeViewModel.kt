package com.example.taskplanner


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
        private val TaskRepository: TaskRepository,
        private val NoteRepository: NoteRepository)
        : ViewModel() {
        val allTasks2 = TaskRepository.allTasks

        val overdueTasks: StateFlow<List<Task>> = allTasks2
            .map { list -> list.filter { it.isOverdue() } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
        fun Task.isOverdue(): Boolean{
            return this.date < LocalDate.now().toString() && !this.isDone
        }

    // TASK SECTION
    val allTasks: StateFlow<List<Task>> =
        TaskRepository.allTasks.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun tasksForDate(date: String) = TaskRepository.getTasksForDate(date)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTask(task: Task) {
        viewModelScope.launch { TaskRepository.insert(task) }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { TaskRepository.update(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { TaskRepository.delete(task) }
    }
    // END OF TASK SECTION

    // NOTE SECTION
    val allNotes: StateFlow<List<Note>> =
        NoteRepository.allNotes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addNote(note: Note) {
        viewModelScope.launch { NoteRepository.insert(note) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch { NoteRepository.update(note) }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { NoteRepository.delete(note) }
    }


}
