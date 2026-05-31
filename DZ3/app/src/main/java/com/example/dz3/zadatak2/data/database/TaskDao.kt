package com.example.dz3.zadatak2.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)

    @Query("DELETE FROM tasks WHERE taskId = :id")
    suspend fun deleteTaskById(id: String)

    @Query("SELECT * FROM tasks WHERE taskId = :id LIMIT 1")
    suspend fun getTaskById(id: String): TaskEntity?

}