package com.example.dz3.zadatak2.data.cache

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("TaskiePrefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "auth_token"
    }

    fun saveToken(token: String) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN_KEY, null)
    }

    fun clearData() {
        prefs.edit().clear().apply()
    }
}