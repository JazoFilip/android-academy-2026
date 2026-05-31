package com.example.dz3.zadatak2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dz3.zadatak2.data.cache.SharedPrefsManager
import com.example.dz3.zadatak2.presentation.EditTaskScreenViewModel
import com.example.dz3.zadatak2.presentation.LoginViewModel
import com.example.dz3.zadatak2.presentation.TaskListScreenViewModel
import com.example.dz3.zadatak2.ui.editTaskScreen.EditTaskScreen
import com.example.dz3.zadatak2.ui.loginScreen.LoginScreen
import com.example.dz3.zadatak2.ui.taskListScreen.TaskListScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val prefsManager = SharedPrefsManager(context)

    val startRoute = if (prefsManager.getToken() != null) "main_screen" else "login_screen"

    NavHost(navController = navController, startDestination = startRoute){

        composable("login_screen") {
            val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
            val uiState by loginViewModel.uiState.collectAsState()

            LoginScreen(
                uiState = uiState,
                onLoginClicked = { email, password ->
                    loginViewModel.login(email, password)
                },
                onResetState = {
                    loginViewModel.resetState()
                },
                onLoginSuccess = { token ->
                    prefsManager.saveToken(token)
                    navController.navigate("main_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                }
            )
        }

        composable("main_screen"){
            val taskListViewModel: TaskListScreenViewModel = viewModel(factory = TaskListScreenViewModel.Factory)
            val uiState by taskListViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                taskListViewModel.getList()
            }

            TaskListScreen(
                uiState = uiState,
                onAddTaskClick = {
                    navController.navigate("edit_note_screen?id=new")
                },
                onTaskClick = { id ->
                    navController.navigate("edit_note_screen?id=$id")
                },
                onDeleteConfirmed = { id ->
                    taskListViewModel.deleteTask(id)
                },
                onRefresh = {
                    taskListViewModel.getList()
                }
            )
        }

        composable(
            route = "edit_note_screen?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val editTaskViewModel: EditTaskScreenViewModel = viewModel(factory = EditTaskScreenViewModel.Factory)
            val uiState by editTaskViewModel.uiState.collectAsState()

            LaunchedEffect(id) {
                editTaskViewModel.loadTask(id)
            }

            EditTaskScreen(
                uiState = uiState,
                onTitleChange = { newTitle ->
                    editTaskViewModel.onTitleChange(newTitle)
                },
                onBodyChange = { newBody ->
                    editTaskViewModel.onBodyChange(newBody)
                },
                onBackClicked = {
                    navController.popBackStack()
                },
                onSaveClicked = {
                    editTaskViewModel.save {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}