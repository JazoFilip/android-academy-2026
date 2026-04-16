package com.example.dz3.zadatak2.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dz3.zadatak2.mainScreen.components.NoteCard
import com.example.dz3.zadatak2.data.Note
import com.example.dz3.zadatak2.data.sampleNotes

@Composable
fun MainScreen(
    notes: List<Note> = sampleNotes,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text("Notes", fontWeight = FontWeight.Medium, fontSize = 30.sp)
            IconButton({
                navController.navigate("edit_note_screen?id=new")
            }){
                Icon(imageVector = Icons.Default.Add,"add Note Button")
            }
        }
        LazyColumn() {
            items(notes, key =  { it.id}) { item ->
                NoteCard(
                    { navController.navigate("edit_note_screen?id=${item.id}")},
                    item
                )
            }
        }
    }
}