package com.example.dz3.zadatak1.task5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dz3.zadatak1.task2.ItemCard
import com.example.dz3.zadatak1.task3.sampleDataList

@Composable
fun ListScreen(
    navController: NavController
)   {
    var list by remember { mutableStateOf(sampleDataList) }
    var query by remember { mutableStateOf("") }

    val filteredList = list.filter {
        it.title.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
                .padding(22.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredList, key = { it.ID }) { item ->
                ItemCard(item,{navController.navigate("detail/${item.ID}")})
            }
        }
    }
}