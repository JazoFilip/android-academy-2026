package com.example.dz3.zadatak2.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dz3.zadatak2.model.GetListResult
import com.example.dz3.zadatak2.model.Note
import com.example.dz3.zadatak2.model.NoteRepository
import com.example.dz3.zadatak2.model.SaveNoteResult
import com.example.dz3.zadatak2.model.noteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class EditScreenViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<EditScreenUIState>(EditScreenUIState.Loading)
    val uiState = _uiState.asStateFlow()

    var title by mutableStateOf("")
    var description by mutableStateOf("")

    private var originalNote: Note? = null

    fun loadNote(id: String?){
        viewModelScope.launch {
            _uiState.value = EditScreenUIState.Loading

            if(id == null || id == "new"){
                originalNote = null
                title = ""
                description = ""
                _uiState.value = EditScreenUIState.Loaded(
                    Note(id = "new", title = "","", LocalDate.now())
                )
            }else{
                val result = repository.getList()
                if( result is GetListResult.Success){
                    val foundNote = result.list.find { it.id == id }
                    if (foundNote != null){
                        originalNote = foundNote
                        title = foundNote.title
                        description = foundNote.description
                        _uiState.value = EditScreenUIState.Loaded(foundNote)
                    }else{
                        _uiState.value = EditScreenUIState.Failure("Note not found")
                    }
                }else{
                    _uiState.value = EditScreenUIState.Failure("Note not found")
                }
            }

        }
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val noteToSave = Note(
                id = originalNote?.id ?: UUID.randomUUID().toString(),
                title = title,
                description = description,
                date = originalNote?.date ?: LocalDate.now()
            )

            val result = repository.saveNote(noteToSave)
            if (result is SaveNoteResult.Success) {
                onSuccess()
            } else {
                _uiState.value = EditScreenUIState.Failure("Could not save note")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
                EditScreenViewModel(noteRepository) as T
        }
    }
}


sealed interface EditScreenUIState {

    data class Loaded(val note: Note) : EditScreenUIState

    data object Loading : EditScreenUIState
    data class Failure(val message: String) : EditScreenUIState
}