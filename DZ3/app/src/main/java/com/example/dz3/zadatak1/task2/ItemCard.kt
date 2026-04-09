package com.example.dz3.zadatak1.task2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dz3.zadatak1.task1.CustomButton
import com.example.dz3.zadatak1.task1.DescriptionText
import com.example.dz3.zadatak1.task1.TitleText


@Preview
@Composable
fun ItemCard(
    myData: MyData = MyData("fewfwefweasdf","Trucking","Never tell me the odds!"),
    onClick: () -> Unit = {}
){
    Card(
        modifier = Modifier.padding(12.dp)
            .size(width = 240.dp, height = 100.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Icon(imageVector = Icons.Default.Abc, contentDescription = "")
                TitleText(myData.title)
            }

            DescriptionText(myData.description)

            Row() {
                CustomButton("Edit")
                CustomButton("Save")
            }
        }
    }
}