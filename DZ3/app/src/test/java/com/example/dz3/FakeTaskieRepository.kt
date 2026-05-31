package com.example.dz3

import com.example.dz3.zadatak2.data.database.TaskEntity
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskRequest
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskResponse
import com.example.dz3.zadatak2.data.model.login.LoginRequest
import com.example.dz3.zadatak2.data.model.login.LoginResponse
import com.example.dz3.zadatak2.data.model.task.GetTaskResponse
import com.example.dz3.zadatak2.data.model.task.PutTaskRequest
import com.example.dz3.zadatak2.data.repository.TaskieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeTaskieRepository : TaskieRepository {

    var isLoginSuccessful: Boolean = true
    var tokenToReturn: String = "profesorov_fake_token"

    override suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return if (isLoginSuccessful) {
            Response.success(LoginResponse(tokenToReturn))
        } else {
            Response.error(401, "Unauthorized".toResponseBody(null))
        }
    }

    override fun getTasksFlow(): Flow<List<TaskEntity>> = flowOf(emptyList())
    override suspend fun refreshTasks(token: String) {}
    override suspend fun updateTask(token: String, id: String, request: PutTaskRequest): Response<Unit> = Response.success(Unit)
    override suspend fun createTask(token: String, request: CreateTaskRequest): Response<CreateTaskResponse> = Response.success(CreateTaskResponse("1"))
    override suspend fun getTaskDetails(token: String, id: String): Response<GetTaskResponse> = Response.success(GetTaskResponse("1", "", ""))
    override suspend fun deleteTask(token: String, id: String): Boolean = true
}