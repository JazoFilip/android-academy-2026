package com.example.dz3.zadatak2

import android.content.Context
import androidx.room.Room
import com.example.dz3.zadatak2.data.cache.SharedPrefsManager
import com.example.dz3.zadatak2.data.database.TaskDatabase
import com.example.dz3.zadatak2.data.repository.RetrofitTaskieRepository
import com.example.dz3.zadatak2.data.repository.TaskieRepository

class AppContainer(private val context: Context) {
    private val database: TaskDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java, "taskie_database"
        ).build()
    }

    val taskieRepository: TaskieRepository by lazy {
        RetrofitTaskieRepository(database.taskDao())
    }

    val prefsManager: SharedPrefsManager by lazy {
        SharedPrefsManager(context.applicationContext)
    }
}