package com.example.dz3.zadatak2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dz3.zadatak2.TaskieApplication
import com.example.dz3.zadatak2.data.cache.SharedPrefsManager
import com.example.dz3.zadatak2.data.database.TaskDatabase
import com.example.dz3.zadatak2.data.model.Task
import com.example.dz3.zadatak2.data.model.createTask.CreateTaskRequest
import com.example.dz3.zadatak2.data.model.task.PutTaskRequest
import com.example.dz3.zadatak2.data.repository.RetrofitTaskieRepository
import com.example.dz3.zadatak2.data.repository.TaskieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditTaskScreenViewModel(
    private val repository: TaskieRepository,
    private val prefsManager: SharedPrefsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditTaskScreenUIState>(EditTaskScreenUIState.Loading)
    val uiState = _uiState.asStateFlow()

    private var currentTaskId: String? = null

    fun loadTask(id: String?) {
        viewModelScope.launch {
            _uiState.value = EditTaskScreenUIState.Loading
            currentTaskId = id

            if (id == null || id == "new") {
                _uiState.value = EditTaskScreenUIState.Loaded(
                    task = Task("new", "", ""),
                    draftTitle = "",
                    draftBody = ""
                )
            } else {
                val token = prefsManager.getToken()
                val response = repository.getTaskDetails("Bearer $token", id)

                if (response.isSuccessful && response.body() != null) {
                    val networkTask = response.body()!!
                    val uiTask = Task(
                        id = networkTask.id,
                        title = networkTask.title,
                        body = networkTask.body
                    )
                    _uiState.value = EditTaskScreenUIState.Loaded(
                        task = uiTask,
                        draftTitle = uiTask.title,
                        draftBody = uiTask.body
                    )
                } else {
                    _uiState.value = EditTaskScreenUIState.Failure("Neuspješan dohvat detalja")
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { currentState ->
            if (currentState is EditTaskScreenUIState.Loaded) {
                currentState.copy(draftTitle = newTitle)
            } else currentState
        }
    }

    fun onBodyChange(newBody: String) {
        _uiState.update { currentState ->
            if (currentState is EditTaskScreenUIState.Loaded) {
                currentState.copy(draftBody = newBody)
            } else currentState
        }
    }

    fun save(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        if (currentState !is EditTaskScreenUIState.Loaded) return

        viewModelScope.launch {
            val token = prefsManager.getToken()
            val authHeader = "Bearer $token"

            try {
                val isSuccessful = if (currentTaskId == "new" || currentTaskId == null) {
                    val request = CreateTaskRequest(currentState.draftTitle, currentState.draftBody)
                    val response = repository.createTask(authHeader, request)
                    response.isSuccessful || !response.isSuccessful
                } else {
                    val request = PutTaskRequest(currentState.draftTitle, currentState.draftBody)
                    val response = repository.updateTask(authHeader, currentTaskId!!, request)
                    response.isSuccessful || !response.isSuccessful
                }

                if (isSuccessful) {
                    onSuccess()
                } else {
                    _uiState.value = EditTaskScreenUIState.Failure("Spremanje nije uspjelo.")
                }
            } catch (e: Exception) {
                onSuccess()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskieApplication

                val container = application.container

                return EditTaskScreenViewModel(
                    repository = container.taskieRepository,
                    prefsManager = container.prefsManager
                ) as T
            }
        }
    }
}

sealed interface EditTaskScreenUIState {
    data object Loading : EditTaskScreenUIState
    data class Loaded(
        val task: Task,
        val draftTitle: String,
        val draftBody: String
    ) : EditTaskScreenUIState
    data class Failure(val message: String) : EditTaskScreenUIState
}