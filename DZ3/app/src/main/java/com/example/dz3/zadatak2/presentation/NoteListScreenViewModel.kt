package com.example.dz3.zadatak2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dz3.zadatak2.model.GetListResult
import com.example.dz3.zadatak2.model.Note
import com.example.dz3.zadatak2.model.NoteRepository
import com.example.dz3.zadatak2.model.noteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteListScreenViewModel( private val repository: NoteRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<NoteListUIState>(NoteListUIState.Loading)

    val uiState = _uiState.asStateFlow()

    fun getList() {
        viewModelScope.launch {
            _uiState.value = NoteListUIState.Loading
            _uiState.value = when(val result = repository.getList()) {
                is GetListResult.Success -> NoteListUIState.Loaded(list = result.list)
                GetListResult.Failure -> NoteListUIState.Failure("Error while loading list")
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
                NoteListScreenViewModel(noteRepository) as T
        }
    }
}

sealed interface NoteListUIState{
    data class Loaded(
        val list: List<Note>
    ) : NoteListUIState
    data object Loading : NoteListUIState

    data class Failure(val message: String) : NoteListUIState
}