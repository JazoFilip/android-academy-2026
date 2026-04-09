package com.example.dz3.zadatak1.task1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun DescriptionText(
    text: String = "Never tell me the odds!"
){
    Text(text, fontSize = 15.sp, maxLines = 3, color = Color.Yellow)
}