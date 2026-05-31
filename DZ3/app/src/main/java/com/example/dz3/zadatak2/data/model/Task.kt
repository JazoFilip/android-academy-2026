package com.example.dz3.zadatak2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("id") val id: String,
    @SerialName("draftTitle") val title: String,
    @SerialName("draftBody") val body: String
)