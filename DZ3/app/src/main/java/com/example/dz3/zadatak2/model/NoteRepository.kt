package com.example.dz3.zadatak2.model

import com.example.dz3.zadatak2.data.NoteLocalStorage
import com.example.dz3.zadatak2.data.NoteRemoteStorage


val noteRepository by lazy {
    NoteRepository(
        localStorage = null,
        remoteStorage = NoteRemoteStorage()
    )
}

class NoteRepository(
    private val localStorage: NoteLocalStorage?,
    private val remoteStorage: NoteRemoteStorage
){

    suspend  fun getList(): GetListResult {
        val remoteList = try {
            remoteStorage.getList()
        } catch (_: Exception){
            return GetListResult.Failure
        }
        return GetListResult.Success(remoteList)
    }

    suspend fun  saveNote(note: Note) : SaveNoteResult {
        try {
            remoteStorage.save(note)
        }catch (e: Exception){
            return SaveNoteResult.Failure
        }
        return SaveNoteResult.Success(note)
    }
}

sealed interface SaveNoteResult {
    data class Success (val note: Note) : SaveNoteResult
    data object Failure : SaveNoteResult
}

sealed interface GetListResult {
    data class Success (val list: List<Note>) : GetListResult
    data object Failure : GetListResult
}