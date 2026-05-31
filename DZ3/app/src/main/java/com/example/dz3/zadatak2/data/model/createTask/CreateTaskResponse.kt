package com.example.dz3.zadatak2.data.model.createTask

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskResponse(
    @SerialName("id") val id: String
)