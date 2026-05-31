package com.example.dz3.zadatak2

import android.app.Application

class TaskieApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}