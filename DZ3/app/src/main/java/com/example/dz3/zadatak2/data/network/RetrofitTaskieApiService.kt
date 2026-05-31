package com.example.dz3.zadatak2.data.network

import com.example.dz3.zadatak2.data.model.Task
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskRequest
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskResponse
import com.example.dz3.zadatak2.data.model.login.LoginRequest
import com.example.dz3.zadatak2.data.model.login.LoginResponse
import com.example.dz3.zadatak2.data.model.task.GetAllTasksResponse
import com.example.dz3.zadatak2.data.model.task.GetTaskResponse
import com.example.dz3.zadatak2.data.model.task.PutTaskRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitTaskieApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("tasks/all")
    suspend fun getTasks(
        @Header("Authorization") token: String
    ): Response<GetAllTasksResponse>

    @POST("tasks/create")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body request: CreateTaskRequest
    ): Response<CreateTaskResponse>

    @GET("tasks/{id}")
    suspend fun getTaskDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<GetTaskResponse>

    @PUT("tasks/{id}")
    suspend fun updateTask(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: PutTaskRequest
    ): Response<Unit>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Response<Unit>
}