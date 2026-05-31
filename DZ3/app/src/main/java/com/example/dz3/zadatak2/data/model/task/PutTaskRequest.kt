package com.example.dz3.zadatak2.data.model.task

import kotlinx.serialization.Serializable

@Serializable
data class PutTaskRequest(
    val title: String,
    val body: String
)