package com.example.taskplanner

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel : ViewModel() {


    // Today's tasks
    private val _todayTasks = MutableStateFlow<List<Task>>(emptyList())
    val todayTasks: StateFlow<List<Task>> = _todayTasks

    // Notes
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        // Dummy tasks for today
        val today = LocalDate.now()
        _todayTasks.value = listOf(
            Task(1, "Finish Compose screen", "Implement all UI components for Home and Tasks screens", dueDate = today),
            Task(2, "Review project tasks", "Check all pending tasks and deadlines", dueDate = today),
            Task(3, "Plan tomorrow's tasks", "Prepare list of tasks for tomorrow", dueDate = today)
        )

        // Dummy notes
        _notes.value = listOf(
            Note(1, "Meeting Notes", "Discuss project timeline and milestones"),
            Note(2, "Shopping List", "Milk, Eggs, Bread, Coffee")
        )
    }

    // Tasks operations
    fun toggleTaskDone(taskId: Int) {
        _todayTasks.update { list ->
            list.map { task ->
                if (task.id == taskId) task.copy(isDone = !task.isDone) else task
            }
        }
    }

    fun addTask(task: Task) {
        _todayTasks.update { list -> list + task }
    }

    fun removeTask(taskId: Int) {
        _todayTasks.update { list -> list.filter { it.id != taskId } }
    }

    // Notes operations
    fun addNote(note: Note) {
        _notes.update { list -> list + note }
    }
    fun getNextNoteId(): Int {
        return _notes.value.maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    fun removeNote(noteId: Int) {
        _notes.update { list -> list.filter { it.id != noteId } }
    }

    // Optional: Get today's tasks only
    fun getTasksForToday(): List<Task> {
        val today = LocalDate.now()
        return _todayTasks.value.filter { it.dueDate == today }
    }
}
