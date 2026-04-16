package com.example.dz3.zadatak2.ui.editNoteScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dz3.zadatak2.presentation.EditScreenUIState
import com.example.dz3.zadatak2.presentation.EditScreenViewModel

@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditScreenViewModel,
    noteId: String?
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    when (val state = uiState) {
        is EditScreenUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFD98874))
            }
        }
        is EditScreenUIState.Failure -> {
            Text("Error: ${state.message}")
        }
        is EditScreenUIState.Loaded -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "back", modifier = Modifier.size(32.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.title = it },
                    placeholder = { Text("Title") },
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD98874)),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = viewModel.description,
                    onValueChange = { viewModel.description = it },
                    placeholder = { Text("Description") },
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD98874)),
                )

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = {
                            viewModel.save { navController.popBackStack() }
                        },
                        modifier = Modifier.fillMaxWidth(0.4f).height(56.dp).padding(bottom = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD98874)),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text("Done", color = Color.White)
                    }
                }
            }
        }
    }
}