package com.example.dz3.zadatak2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dz3.zadatak2.TaskieApplication
import com.example.dz3.zadatak2.data.database.TaskDatabase
import com.example.dz3.zadatak2.data.model.login.LoginRequest
import com.example.dz3.zadatak2.data.repository.RetrofitTaskieRepository
import com.example.dz3.zadatak2.data.repository.TaskieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: TaskieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUIState.Loading
            try {
                val request = LoginRequest(email, password)
                val response = repository.loginUser(request)

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    _uiState.value = LoginUIState.Success(token)
                } else {
                    _uiState.value = LoginUIState.Failure("Pogrešan email ili lozinka (Kôd: ${response.code()})")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUIState.Failure("Mrežna greška: ${e.localizedMessage}")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUIState.Idle
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskieApplication
                val container = application.container

                return LoginViewModel(repository = container.taskieRepository) as T
            }
        }
    }
}

sealed interface LoginUIState {
    data object Idle : LoginUIState
    data object Loading : LoginUIState
    data class Success(val token: String) : LoginUIState
    data class Failure(val message: String) : LoginUIState
}