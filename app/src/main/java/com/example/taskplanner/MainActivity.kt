@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.taskplanner

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskplanner.data.entities.Task
import com.example.taskplanner.data.entities.Note
import com.example.taskplanner.data.repository.NoteRepository
import com.example.taskplanner.data.repository.TaskRepository
import com.example.taskplanner.data.database.TaskDatabase
import com.example.taskplanner.ui.theme.TaskPlannerTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database and repositories
        val db = TaskDatabase.getDatabase(application)
        val taskRepository = TaskRepository(db.taskDao())
        val noteRepository = NoteRepository(db.noteDao())

        // Initialize ViewModelFactory
        val factory = HomeViewModelFactory(taskRepository, noteRepository)

        setContent {
            TaskPlannerTheme {
                val navController = rememberNavController()
                val viewModel: HomeViewModel = viewModel(factory = factory)

                val today = Calendar.getInstance()
                val day = today.get(Calendar.DAY_OF_MONTH)
                val month = today.get(Calendar.MONTH) + 1 // Months start at 0
                val year = today.get(Calendar.YEAR)
                val dateString = "$year-$month-$day" // Format as you like

                Surface(color = MaterialTheme.colorScheme.background) {
                    AppScaffold(logoResId = R.drawable.app_logo) {
                        NavHost(navController = navController, startDestination = "home") {

                            // Home Screen
                            composable("home") {
                                HomeScreen(
                                    viewModel = viewModel,
                                    onTodayClick = { navController.navigate("todays_tasks") },
                                    onUpcomingClick = { navController.navigate("upcoming_tasks") },
                                    onAddTaskClick = { navController.navigate("addTask") },
                                    onAddNoteClick = { navController.navigate("addNote") }
                                )
                            }

                            // Add Task Screen
                            composable("addTask") {
                                AddTaskScreen(
                                    viewModel = viewModel,
                                    onSaveTask = { navController.popBackStack() },
                                    onCancel = { navController.popBackStack() },
                                    onAddTask = { title, description ->
                                        val task = Task(
                                            title = title,
                                            description = description,
                                            date = dateString,
                                            isDone = false
                                        )
                                        viewModel.addTask(task)
                                    }
                                )
                            }

                            // Add Note Screen
                            composable("addNote") {
                                AddNotesScreen(
                                    viewModel = viewModel,
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            // Today's Tasks Screen
                            composable("todays_tasks") {
                                TodaysTasksScreen(
                                    viewModel = viewModel,
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            // Upcoming Tasks Screen
                            composable("upcoming_tasks") {
                                UpcomingTasksScreen(
                                    viewModel = viewModel,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}