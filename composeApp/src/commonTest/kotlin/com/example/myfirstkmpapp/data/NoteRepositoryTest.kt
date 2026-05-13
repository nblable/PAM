package com.example.myfirstkmpapp.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NoteRepositoryTest {
    private lateinit var repository: NoteRepository

    @BeforeTest
    fun setup() {
        repository = InMemoryNoteRepository()
    }

    // 1. Insert Note
    @Test
    fun insertNote_savesToDatabaseSuccessfully() = runTest {
        // Arrange
        val title = "Test Note"
        val content = "Test Content"

        // Act
        repository.insertNote(title, content)
        val notes = repository.getAllNotes().first()

        // Assert
        val savedNote = notes.find { it.title == title }
        assertNotNull(savedNote)
        assertEquals(content, savedNote.content)
    }

    // 2. Get All Notes
    @Test
    fun getAllNotes_returnsCorrectListOfNotes() = runTest {
        // Arrange & Act
        val notes = repository.getAllNotes().first()

        // Assert
        assertEquals(3, notes.size) // Default dummyNotes size is 3
    }

    // 3. Get Note By Id (Success)
    @Test
    fun getNoteById_returnsCorrectNoteWhenExists() = runTest {
        // Arrange
        val notes = repository.getAllNotes().first()
        val firstNoteId = notes.first().id

        // Act
        val note = repository.getNoteById(firstNoteId)

        // Assert
        assertNotNull(note)
        assertEquals(firstNoteId, note.id)
    }

    // 4. Get Note By Id (Not Found)
    @Test
    fun getNoteById_returnsNullWhenNotFound() = runTest {
        // Act
        val note = repository.getNoteById(999)

        // Assert
        assertNull(note)
    }

    // 5. Delete Note (Success)
    @Test
    fun deleteNote_removesNoteFromDatabase() = runTest {
        // Arrange
        val notesBefore = repository.getAllNotes().first()
        val firstNoteId = notesBefore.first().id

        // Act
        repository.deleteNote(firstNoteId)
        val notesAfter = repository.getAllNotes().first()

        // Assert
        assertEquals(notesBefore.size - 1, notesAfter.size)
        assertNull(notesAfter.find { it.id == firstNoteId })
    }

    // 6. Delete Note (Invalid Id)
    @Test
    fun deleteNote_doesNothingWhenIdDoesNotExist() = runTest {
        // Arrange
        val notesBefore = repository.getAllNotes().first()
        
        // Act
        repository.deleteNote(999)
        val notesAfter = repository.getAllNotes().first()
        
        // Assert
        assertEquals(notesBefore.size, notesAfter.size)
    }

    // 7. Update Note (Success)
    @Test
    fun updateNote_modifiesExistingNoteData() = runTest {
        // Arrange
        val notes = repository.getAllNotes().first()
        val noteId = notes.first().id
        val newTitle = "Updated Title"
        val newContent = "Updated Content"

        // Act
        repository.updateNote(noteId, newTitle, newContent)
        val updatedNote = repository.getNoteById(noteId)

        // Assert
        assertNotNull(updatedNote)
        assertEquals(newTitle, updatedNote.title)
        assertEquals(newContent, updatedNote.content)
    }

    // 8. Update Note (Invalid Id)
    @Test
    fun updateNote_doesNothingWhenIdDoesNotExist() = runTest {
        // Arrange
        val notesBefore = repository.getAllNotes().first()
        
        // Act
        repository.updateNote(999, "Title", "Content")
        val notesAfter = repository.getAllNotes().first()
        
        // Assert
        assertEquals(notesBefore, notesAfter)
    }

    // 9. Toggle Favorite (True)
    @Test
    fun toggleFavorite_changesFavoriteStatus() = runTest {
        // Arrange
        val notes = repository.getAllNotes().first()
        val noteId = notes.find { !it.isFavorite }?.id ?: 1

        // Act
        repository.toggleFavorite(noteId)
        val updatedNote = repository.getNoteById(noteId)

        // Assert
        assertNotNull(updatedNote)
        assertTrue(updatedNote.isFavorite)
    }

    // 10. Toggle Favorite (False)
    @Test
    fun toggleFavorite_revertsFavoriteStatus() = runTest {
        // Arrange
        val notes = repository.getAllNotes().first()
        val noteId = notes.find { it.isFavorite }?.id ?: 1
        
        // Act
        repository.toggleFavorite(noteId)
        val updatedNote = repository.getNoteById(noteId)

        // Assert
        assertNotNull(updatedNote)
        assertEquals(false, updatedNote.isFavorite)
    }
}
