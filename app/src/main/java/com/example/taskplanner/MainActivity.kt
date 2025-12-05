package com.example.taskplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: HomeViewModel = viewModel()

            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(navController = navController, startDestination = "home") {

                    // Home Screen
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onTodayClick = { navController.navigate("todays_tasks") },
                            onUpcomingClick = { navController.navigate("upcoming_tasks") }, // placeholder
                            onAddTaskClick = { navController.navigate("addTask") },
                            onAddNoteClick = { navController.navigate("addNote") } // placeholder
                        )
                    }

                    // Todays Tasks Screen
                    composable("todays_tasks") {
                        val todayTasks = listOf(
                            Task(1, "Finish Compose screen"),
                            Task(2, "Review project tasks"),
                            Task(3, "Plan tomorrow's tasks")
                        )

                        TodaysTasksScreen(
                            tasks = todayTasks,
                            onTaskClick = { task ->
                                println("Clicked on: ${task.title}")
                            },
                            onBack = { navController.popBackStack() } // back to home
                        )
                    }

                    // Add Task Screen
                    composable("addTask") {
                        AddTaskScreen(
                            onSaveTask = { task ->
                                viewModel.addTask(task)
                                navController.popBackStack()
                            },
                            onCancel = { navController.popBackStack() }
                        )
                    }

                    // Upcoming Tasks Screen (placeholder)
                    composable("upcoming_tasks") {
                        // TODO: Implement UpcomingTasksScreen
                    }

                    // Add Note Screen (placeholder)
                    composable("addNote") {
                        // TODO: Implement AddNoteScreen
                    }
                }
            }
        }
    }
}
