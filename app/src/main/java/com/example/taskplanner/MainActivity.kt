@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.taskplanner

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskplanner.ui.theme.TaskPlannerTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskPlannerTheme {
                val navController = rememberNavController()
                val viewModel: HomeViewModel = viewModel()

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
                                    onSaveTask = {
                                        // Task object already added inside AddTaskScreen
                                        navController.popBackStack()
                                    },
                                    onCancel = { navController.popBackStack() }
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
