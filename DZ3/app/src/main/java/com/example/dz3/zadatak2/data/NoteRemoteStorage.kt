package com.example.dz3.zadatak2.data

import com.example.dz3.zadatak2.model.Note
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.random.Random
import kotlin.random.nextLong

class NoteRemoteStorage{

    private val list = mutableListOf(
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

    suspend fun getList(): List<Note> {
        simulateNetworkCommunication()
        return list
    }

    suspend fun save(note: Note) {
        simulateNetworkCommunication()
        val indexOfItem = list.indexOfFirst { it.id == note.id }
        if (indexOfItem >= 0){
            list[indexOfItem] = note
        }else{
            list.add(note)
        }

    }

    private suspend fun simulateNetworkCommunication(){
        delay(Random.nextLong(300L..800L))

    }
}