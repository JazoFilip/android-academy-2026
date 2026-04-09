package com.example.dz3.zadatak1.task1


import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CustomButton(
    text: String = "Click"
){
    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)) {
        Text(text, color = Color.Gray)
    }
}