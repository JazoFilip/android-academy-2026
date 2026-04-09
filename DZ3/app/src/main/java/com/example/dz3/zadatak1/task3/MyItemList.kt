package com.example.dz3.zadatak1.task3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dz3.zadatak1.task2.ItemCard
import com.example.dz3.zadatak1.task2.MyData

val sampleDataList = List(20) { index ->
    MyData(
        ID = index.toString(),
        title = "Title $index",
        description = "Description for item $index"
    )
}

@Preview(showBackground = true)
@Composable
fun MyItemList(
    data: List<MyData> = sampleDataList
){
    var list by remember { mutableStateOf(data) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ){
        Button(
            onClick = {
                list = list.shuffled()
            },
        ) {
            Text("Shuffle")
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { item ->
                ItemCard(item)
            }
        }
    }

}