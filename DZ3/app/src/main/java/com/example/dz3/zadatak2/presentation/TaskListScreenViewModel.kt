package com.example.dz3.zadatak2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dz3.zadatak2.TaskieApplication
import com.example.dz3.zadatak2.data.cache.SharedPrefsManager
import com.example.dz3.zadatak2.data.database.TaskDatabase
import com.example.dz3.zadatak2.data.model.Task
import com.example.dz3.zadatak2.data.repository.RetrofitTaskieRepository
import com.example.dz3.zadatak2.data.repository.TaskieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TaskListScreenViewModel(
    private val repository: TaskieRepository,
    private val prefsManager: SharedPrefsManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<TaskListUIState>(TaskListUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        observeLocalDatabase()
    }

    private fun observeLocalDatabase() {
        viewModelScope.launch {
            repository.getTasksFlow()
                .catch { e ->
                    _uiState.value = TaskListUIState.Failure("Greška pri čitanju baze: ${e.localizedMessage}")
                }
                .collect { entityList ->
                    val uiTasks = entityList.map { entity ->
                        Task(
                            id = entity.taskId,
                            title = entity.title,
                            body = entity.body
                        )
                    }
                    _uiState.value = TaskListUIState.Loaded(list = uiTasks)
                }
        }
    }

    fun getList() {
        viewModelScope.launch {
            val token = prefsManager.getToken()
            if (token.isNullOrEmpty()) {
                _uiState.value = TaskListUIState.Failure("Korisnik nije ulogiran (token nedostaje)")
                return@launch
            }

            try {
                repository.refreshTasks("Bearer $token")
            } catch (e: Exception) {

            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            val token = prefsManager.getToken()
            if (!token.isNullOrEmpty()) {
                repository.deleteTask("Bearer $token", id)
                getList()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskieApplication

                val container = application.container

                return TaskListScreenViewModel(
                    repository = container.taskieRepository,
                    prefsManager = container.prefsManager
                ) as T
            }
        }
    }
}

sealed interface TaskListUIState{
    data class Loaded(val list: List<Task>) : TaskListUIState
    data object Loading : TaskListUIState
    data class Failure(val message: String) : TaskListUIState
}