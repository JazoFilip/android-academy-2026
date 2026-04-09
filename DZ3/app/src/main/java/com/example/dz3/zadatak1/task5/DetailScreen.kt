package com.example.dz3.zadatak1.task5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dz3.zadatak1.task1.DescriptionText
import com.example.dz3.zadatak1.task1.TitleText
import com.example.dz3.zadatak1.task3.sampleDataList

@Composable
fun DetailScreen(navController: NavController, id: String?) {

    val item = sampleDataList.find { it.ID == id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TitleText(item?.title ?: "")
        DescriptionText(item?.description ?: "")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }
    }
}