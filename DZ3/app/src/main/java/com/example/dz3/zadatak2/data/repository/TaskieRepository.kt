package com.example.dz3.zadatak2.data.repository

import com.example.dz3.zadatak2.data.database.TaskEntity
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskRequest
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskResponse
import com.example.dz3.zadatak2.data.model.login.LoginRequest
import com.example.dz3.zadatak2.data.model.login.LoginResponse
import com.example.dz3.zadatak2.data.model.task.GetAllTasksResponse
import com.example.dz3.zadatak2.data.model.task.GetTaskResponse
import com.example.dz3.zadatak2.data.model.task.PutTaskRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TaskieRepository {
    fun getTasksFlow(): Flow<List<TaskEntity>>
    suspend fun refreshTasks(token: String)
    suspend fun updateTask(token: String, id: String, request: PutTaskRequest) : Response<Unit>
    suspend fun createTask(token: String, request: CreateTaskRequest): Response<CreateTaskResponse>
    suspend fun loginUser(request: LoginRequest) : Response<LoginResponse>
    suspend fun getTaskDetails(token: String, id: String) : Response<GetTaskResponse>

    suspend fun deleteTask(token: String, id: String): Boolean
}