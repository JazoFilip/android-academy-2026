    package com.example.dz3.zadatak2

    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.example.dz3.zadatak2.data.sampleNotes
    import com.example.dz3.zadatak2.editNoteScreen.EditNoteScreen
    import com.example.dz3.zadatak2.mainScreen.MainScreen

    @Composable
    fun Navigation(){
        val navController = rememberNavController()

        var notes by remember { mutableStateOf(sampleNotes) }

        NavHost(navController = navController, startDestination = "main_screen"){

            composable("main_screen"){
                MainScreen(notes, navController)
            }

            composable("edit_note_screen?id={id}") { backStackEntry ->

                val id = backStackEntry.arguments?.getString("id")

                val note = notes.find { it.id == id }
                EditNoteScreen(
                    navController = navController,
                    note = note,
                    onSave = { updateNote ->
                        val exists = notes.any {it.id == updateNote.id }
                        notes = if(exists) {
                            notes.map { if (it.id == updateNote.id) updateNote else it }
                        }else {
                            notes + updateNote
                        }
                        navController.popBackStack()
                    }
                    )
            }
        }
    }