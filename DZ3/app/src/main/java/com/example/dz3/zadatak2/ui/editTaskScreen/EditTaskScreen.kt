package com.example.dz3.zadatak2.ui.editTaskScreen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dz3.zadatak2.presentation.EditTaskScreenUIState

@Composable
fun EditTaskScreen(
    uiState: EditTaskScreenUIState,
    onTitleChange: (String) -> Unit,
    onBodyChange: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    when (uiState) {
        is EditTaskScreenUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFD98874))
            }
        }
        is EditTaskScreenUIState.Failure -> {
            Text("Error: ${uiState.message}", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
        is EditTaskScreenUIState.Loaded -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
            ) {
                IconButton(onClick = onBackClicked) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "back", modifier = Modifier.size(32.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = uiState.draftTitle,
                    onValueChange = onTitleChange,
                    placeholder = { Text("Title") },
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD98874)),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = uiState.draftBody,
                    onValueChange = onBodyChange,
                    placeholder = { Text("Description") },
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD98874)),
                )

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    Button(
                        onClick = onSaveClicked,
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