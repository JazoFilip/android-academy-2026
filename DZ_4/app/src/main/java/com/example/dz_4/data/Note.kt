package com.example.dz3.zadatak2.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate
)

val sampleNotes = listOf(
    Note(
        id = "1",
        title = "Morning Run",
        description = "Complete 5km in the park before work.",
        date = LocalDate.now()
    ),
    Note(
        id = "2",
        title = "Grocery List",
        description = "Milk, eggs, sourdough bread, and avocados.",
        date = LocalDate.now().minusDays(1)
    ),
    Note(
        id = "3",
        title = "Project Deadline",
        description = "Submit the final prototype for the Android app.",
        date = LocalDate.of(2026, 5, 15)
    ),
    Note(
        id = "4",
        title = "Book Club",
        description = "Discuss the first three chapters of 'Project Hail Mary'.",
        date = LocalDate.of(2026, 4, 20)
    ),
    Note(
        id = "5",
        title = "Car Service",
        description = "Oil change and tire rotation at the downtown garage.",
        date = LocalDate.of(2026, 3, 10)
    )
)
