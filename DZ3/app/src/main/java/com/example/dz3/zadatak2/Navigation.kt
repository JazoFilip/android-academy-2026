    package com.example.dz3.zadatak2

    import androidx.compose.runtime.Composable

    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.example.dz3.zadatak2.presentation.EditScreenViewModel
    import com.example.dz3.zadatak2.presentation.NoteListScreenViewModel
    import com.example.dz3.zadatak2.ui.editNoteScreen.EditNoteScreen
    import com.example.dz3.zadatak2.ui.noteListScreen.NoteListScreen


    @Composable
    fun Navigation(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "main_screen"){

            composable("main_screen"){
                val viewModel : NoteListScreenViewModel = viewModel(factory = NoteListScreenViewModel.Factory)
                NoteListScreen(navController, viewModel)
            }

            composable("edit_note_screen?id={id}") { backStackEntry ->

                val id = backStackEntry.arguments?.getString("id")
                val editViewModel: EditScreenViewModel = viewModel(factory = EditScreenViewModel.Factory)

                EditNoteScreen(
                    navController = navController,
                    viewModel = editViewModel,
                    noteId = id
                )

            }
        }
    }