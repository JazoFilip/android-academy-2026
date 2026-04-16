package com.example.dz3.zadatak2.ui.noteListScreen.components

import androidx.compose.foundation.BorderStroke
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
import com.example.dz3.zadatak2.model.Note
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun NoteCard(
    onClick: () -> Unit = {},
    note: Note = Note("3","Shopping","milk,bread,eggs,rice", LocalDate.now())
){
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val formattedDate = note.date.format(dateFormatter)

    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .defaultMinSize(minHeight = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}