package com.example.dz3.zadatak2.data.repository

import com.example.dz3.zadatak2.data.database.TaskDao
import com.example.dz3.zadatak2.data.database.TaskEntity
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskRequest
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskResponse
import com.example.dz3.zadatak2.data.model.login.LoginRequest
import com.example.dz3.zadatak2.data.model.login.LoginResponse
import com.example.dz3.zadatak2.data.model.task.GetTaskResponse
import com.example.dz3.zadatak2.data.model.task.PutTaskRequest
import com.example.dz3.zadatak2.data.network.RetrofitTaskieInstance
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RetrofitTaskieRepository(private val taskDao: TaskDao) : TaskieRepository {

    override fun getTasksFlow(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    override suspend fun refreshTasks(token: String) {
        try {
            val response = RetrofitTaskieInstance.apiService.getTasks(token)
            if (response.isSuccessful && response.body() != null) {
                val networkTasks = response.body()!!.tasks

                val entities = networkTasks.map { networkTask ->
                    TaskEntity(
                        taskId = networkTask.id,
                        title = networkTask.title,
                        body = networkTask.body,
                        isSynced = true
                    )
                }
                taskDao.insertAll(entities)
            }
        } catch (e: Exception) {

        }
    }

    override suspend fun deleteTask(token: String, id: String): Boolean {
        return try {
            taskDao.deleteTaskById(id)
            val response = RetrofitTaskieInstance.apiService.deleteTask(token, id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return RetrofitTaskieInstance.apiService.login(request)
    }

    override suspend fun getTaskDetails(token: String, id: String): Response<GetTaskResponse> {
        return try {
            val response = RetrofitTaskieInstance.apiService.getTaskDetails(token, id)

            if (response.isSuccessful && response.body() != null) {
                response
            } else {
                getTaskFromLocalFallback(id)
            }
        } catch (e: Exception) {
            getTaskFromLocalFallback(id)
        }
    }
    private suspend fun getTaskFromLocalFallback(id: String): Response<GetTaskResponse> {
        val localEntity = taskDao.getTaskById(id)
        return if (localEntity != null) {
            val localTaskResponse = GetTaskResponse(
                id = localEntity.taskId,
                title = localEntity.title,
                body = localEntity.body
            )
            Response.success(localTaskResponse)
        } else {
            Response.error(404, okhttp3.ResponseBody.create(null, "Task not found locally or online"))
        }
    }

    override suspend fun createTask(token: String, request: CreateTaskRequest): Response<CreateTaskResponse> {
        val tempLocalId = "temp_${System.currentTimeMillis()}"
        val offlineTask = TaskEntity(
            taskId = tempLocalId,
            title = request.title,
            body = request.body,
            isSynced = false
        )
        taskDao.insertTask(offlineTask)

        return try {
            val response = RetrofitTaskieInstance.apiService.createTask(token, request)

            if (response.isSuccessful && response.body() != null) {
                val serverId = response.body()!!.id
                taskDao.deleteTaskById(tempLocalId)
                taskDao.insertTask(
                    TaskEntity(
                        taskId = serverId,
                        title = request.title,
                        body = request.body,
                        isSynced = true
                    )
                )
            }
            response
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }
    }

    override suspend fun updateTask(token: String, id: String, request: PutTaskRequest): Response<Unit> {
        val updatedTask = TaskEntity(
            taskId = id,
            title = request.title,
            body = request.body,
            isSynced = false
        )
        taskDao.insertTask(updatedTask)

        return try {
            val response = RetrofitTaskieInstance.apiService.updateTask(token, id, request)

            if (response.isSuccessful) {
                taskDao.insertTask(updatedTask.copy(isSynced = true))
            }
            response
        } catch (e: Exception) {
            Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }
    }
}