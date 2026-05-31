package com.example.dz3.zadatak2.ui.taskListScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dz3.zadatak2.data.model.Task
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun TaskCard(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    task: Task = Task("1", "Shopping", "milk,bread,eggs,rice")
){
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .defaultMinSize(minHeight = 80.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}