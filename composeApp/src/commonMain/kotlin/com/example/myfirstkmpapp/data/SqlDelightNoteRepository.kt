package com.example.myfirstkmpapp.data

import com.example.myfirstkmpapp.db.MyDatabase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightNoteRepository(database: MyDatabase) : NoteRepository {
    private val queries = database.noteQueries

    override fun getAllNotes(): Flow<List<Note>> {
        return queries.selectAllNewest()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { it.toNote() }
            }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return queries.selectById(id.toLong()).executeAsOneOrNull()?.toNote()
    }

    override suspend fun insertNote(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        queries.insert(title, content, 0, now, now)
    }

    override suspend fun updateNote(id: Int, title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        val existing = getNoteById(id)
        queries.update(title, content, if (existing?.isFavorite == true) 1 else 0, now, id.toLong())
    }

    override suspend fun deleteNote(id: Int) {
        queries.deleteById(id.toLong())
    }

    override suspend fun toggleFavorite(id: Int) {
        val now = Clock.System.now().toEpochMilliseconds()
        queries.toggleFavorite(now, id.toLong())
    }

    override fun searchNotes(query: String): Flow<List<Note>> {
        val pattern = "%$query%"
        return queries.searchNewest(pattern, pattern)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map { it.toNote() }
            }
    }

    private fun com.example.myfirstkmpapp.db.Note.toNote(): Note {
        return Note(
            id = id.toInt(),
            title = title,
            content = content,
            isFavorite = isFavorite == 1L
        )
    }
}