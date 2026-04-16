package com.example.dz3.zadatak2.model

import java.time.LocalDate

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate
)
