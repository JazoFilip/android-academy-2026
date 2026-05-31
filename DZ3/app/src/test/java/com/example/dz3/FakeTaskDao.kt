package com.example.dz3

import com.example.dz3.zadatak2.data.database.TaskDao
import com.example.dz3.zadatak2.data.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTaskDao : TaskDao {
    private val tasksTable = mutableListOf<TaskEntity>()
    private val tasksFlow = MutableStateFlow<List<TaskEntity>>(emptyList())

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return tasksFlow
    }

    override suspend fun insertTask(task: TaskEntity) {
        tasksTable.removeAll { it.taskId == task.taskId }
        tasksTable.add(task)
        tasksFlow.value = tasksTable.toList()
    }

    override suspend fun insertAll(tasks: List<TaskEntity>) {
        tasks.forEach { insertTask(it) }
    }

    override suspend fun deleteTaskById(id: String) {
        tasksTable.removeAll { it.taskId == id }
        tasksFlow.value = tasksTable.toList()
    }

    override suspend fun getTaskById(id: String): TaskEntity? {
        return tasksTable.find { it.taskId == id }
    }
}