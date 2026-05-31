package com.example.dz3.zadatak2.data.model.createTask

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest (
    @SerialName("draftTitle") val title: String,
    @SerialName("draftBody") val body: String

)