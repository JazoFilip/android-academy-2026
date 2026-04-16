package com.example.dz3.zadatak2.editNoteScreen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dz3.zadatak2.data.Note
import com.example.dz3.zadatak2.data.sampleNotes
import java.time.LocalDate
import java.util.UUID


@Composable
fun EditNoteScreen(
    navController: NavController,
    note: Note?,
    onSave: (Note) -> Unit
) {


    var title by remember { mutableStateOf(note?.title ?: "") }
    var description by remember { mutableStateOf(note?.description ?: "") }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
    ) {
        IconButton(
            onClick = { navController.popBackStack()}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.size(32.dp)
            )
        }
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("title") },
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD98874)),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFD98874)),
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        val updatedNote = Note(
                            id = note?.id ?: UUID.randomUUID().toString(),
                            title = title,
                            description = description,
                            date = note?.date ?: LocalDate.now()
                        )
                        onSave(updatedNote)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(56.dp)
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD98874)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Done", color = Color.White)
                }
            }
        }
    }