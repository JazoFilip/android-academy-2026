package com.example.dz3.zadatak1.task1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun TitleText(
    text: String = "Trucking"
){
    Text(text, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
}