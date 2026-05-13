package com.example.myfirstkmpapp.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(title: String, content: String)
    suspend fun updateNote(id: Int, title: String, content: String)
    suspend fun deleteNote(id: Int)
    suspend fun toggleFavorite(id: Int)
    fun searchNotes(query: String): Flow<List<Note>>
}
