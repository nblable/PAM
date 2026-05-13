package com.example.myfirstkmpapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryNoteRepository : NoteRepository {
    private val _notes = MutableStateFlow(dummyNotes)
    private var nextId = dummyNotes.size + 1

    override fun getAllNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override suspend fun getNoteById(id: Int): Note? {
        return _notes.value.find { it.id == id }
    }

    override suspend fun insertNote(title: String, content: String) {
        _notes.update { current ->
            current + Note(nextId++, title, content)
        }
    }

    override suspend fun updateNote(id: Int, title: String, content: String) {
        _notes.update { current ->
            current.map {
                if (it.id == id) it.copy(title = title, content = content) else it
            }
        }
    }

    override suspend fun deleteNote(id: Int) {
        _notes.update { current ->
            current.filter { it.id != id }
        }
    }

    override suspend fun toggleFavorite(id: Int) {
        _notes.update { current ->
            current.map {
                if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        return _notes.map { notes ->
            notes.filter { it.title.contains(query, ignoreCase = true) || it.content.contains(query, ignoreCase = true) }
        }
    }
}
