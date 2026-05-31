package com.example.dz3.zadatak2.data.model.task

import com.example.dz3.zadatak2.data.model.Task
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class GetAllTasksResponse(
    @SerialName("tasks") val tasks: List<GetTaskResponse>
)