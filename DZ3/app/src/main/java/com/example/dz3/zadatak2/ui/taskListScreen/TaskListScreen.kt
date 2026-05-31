package com.example.dz3.zadatak2.ui.taskListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dz3.zadatak2.presentation.TaskListUIState
import com.example.dz3.zadatak2.ui.taskListScreen.components.TaskCard
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.Lifecycle

@Composable
fun TaskListScreen(
    uiState: TaskListUIState,
    onAddTaskClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    onDeleteConfirmed: (String) -> Unit,
    onRefresh: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskIdToDelete by remember { mutableStateOf<String?>(null) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onRefresh()
    }

    if (showDeleteDialog && taskIdToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                taskIdToDelete = null
            },
            title = { Text("Obriši zadatak") },
            text = { Text("Jeste li sigurni da želite obrisati ovaj zadatak?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteConfirmed(taskIdToDelete!!)
                        showDeleteDialog = false
                        taskIdToDelete = null
                    }
                ) {
                    Text("Da", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        taskIdToDelete = null
                    }
                ) {
                    Text("Ne")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text("Tasks", fontWeight = FontWeight.Medium, fontSize = 30.sp)
            IconButton(onClick = onAddTaskClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task Button")
            }
        }

        when (uiState) {
            is TaskListUIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is TaskListUIState.Failure -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.message, color = Color.Red)
                }
            }
            is TaskListUIState.Loaded -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.list, key = { it.id }) { item ->
                        TaskCard(
                            onClick = { onTaskClick(item.id) },
                            onLongClick = {
                                taskIdToDelete = item.id
                                showDeleteDialog = true
                            },
                            task = item
                        )
                    }
                }
            }
        }
    }
}