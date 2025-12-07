package com.example.taskplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskplanner.data.repository.TaskRepository
import com.example.taskplanner.data.repository.NoteRepository
/*
HomeViewModelFactory is to stop the app crashing upon launch

App doesn't allow for boot off of viewModel(), needs factory to initiate it.
 */
class HomeViewModelFactory(
    private val TaskRepository: TaskRepository,
    private val NoteRepository: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(TaskRepository, NoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
