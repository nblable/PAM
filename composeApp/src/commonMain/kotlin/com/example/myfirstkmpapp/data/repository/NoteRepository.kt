package com.example.myfirstkmpapp.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myfirstkmpapp.db.Note
import com.example.myfirstkmpapp.db.MyDatabase
import com.example.myfirstkmpapp.platform.getCurrentTimeMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class NoteRepository(database: MyDatabase) {
    private val queries = database.noteQueries

    fun getNotesNewest(q: String): Flow<List<Note>> =
        queries.searchNewest(q, q).asFlow().mapToList(Dispatchers.IO)

    fun getNotesOldest(q: String): Flow<List<Note>> =
        queries.searchOldest(q, q).asFlow().mapToList(Dispatchers.IO)

    fun insertNote(t: String, c: String) {
        val now = getCurrentTimeMillis()
        queries.insert(t, c, now, now)
    }

    fun updateNote(id: Long, t: String, c: String) {
        queries.update(t, c, getCurrentTimeMillis(), id)
    }

    fun deleteNote(id: Long) = queries.deleteById(id)
}