package com.example.myfirstkmpapp.viewmodel

import app.cash.turbine.test
import com.example.myfirstkmpapp.data.Note
import com.example.myfirstkmpapp.data.NoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {
    private lateinit var viewModel: NotesViewModel
    private val repository: NoteRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val fakeNotes = listOf(
        Note(1, "Title 1", "Content 1"),
        Note(2, "Title 2", "Content 2")
    )
    private val notesFlow = MutableStateFlow(fakeNotes)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.getAllNotes() } returns notesFlow
        viewModel = NotesViewModel(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- UNIT TESTS (MOCKK) ---

    // 11. Load Notes Success
    @Test
    fun loadNotes_updatesUiStateSuccessfully() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeNotes, awaitItem())
        }
    }

    // 12. Add Note
    @Test
    fun addNote_callsRepositoryInsert() = runTest {
        val title = "New Note"
        val content = "New Content"
        coEvery { repository.insertNote(any(), any()) } returns Unit

        viewModel.addNote(title, content)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.insertNote(title, content) }
    }

    // 13. Delete Note
    @Test
    fun deleteNote_callsRepositoryDelete() = runTest {
        val id = 1
        coEvery { repository.deleteNote(any()) } returns Unit

        viewModel.deleteNote(id)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.deleteNote(id) }
    }

    // 14. Update Note
    @Test
    fun updateNote_callsRepositoryUpdate() = runTest {
        val id = 1
        val title = "Updated"
        val content = "Updated"
        coEvery { repository.updateNote(any(), any(), any()) } returns Unit

        viewModel.updateNote(id, title, content)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.updateNote(id, title, content) }
    }

    // --- FLOW TESTS (TURBINE) ---

    // 15. Flow Loading to Success
    @Test
    fun notesStateFlow_emitsInitialEmptyThenData() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeNotes, awaitItem())
        }
    }

    // 16. Flow Emits Updated List After Insertion
    @Test
    fun notesStateFlow_emitsUpdatedListAfterInsertion() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeNotes, awaitItem())

            val updatedNotes = fakeNotes + Note(3, "Title 3", "Content 3")
            notesFlow.value = updatedNotes

            assertEquals(updatedNotes, awaitItem())
        }
    }

    // 17. Flow Reflects Multiple Changes
    @Test
    fun notesStateFlow_reflectsMultipleChanges() = runTest {
        viewModel.notes.test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(fakeNotes, awaitItem())

            val list1 = fakeNotes.filter { it.id != 1 }
            notesFlow.value = list1
            assertEquals(list1, awaitItem())

            val list2 = list1 + Note(4, "Title 4", "Content 4")
            notesFlow.value = list2
            assertEquals(list2, awaitItem())
        }
    }

    // 18. Toggle Favorite
    @Test
    fun toggleFavorite_callsRepositoryToggle() = runTest {
        val id = 1
        coEvery { repository.toggleFavorite(any()) } returns Unit

        viewModel.toggleFavorite(id)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { repository.toggleFavorite(id) }
    }

    // 19. Search Notes
    @Test
    fun searchNotes_updatesUiStateWithResults() = runTest {
        val query = "Title 1"
        val searchResults = listOf(fakeNotes[0])
        val searchFlow = MutableStateFlow(searchResults)
        
        coEvery { repository.searchNotes(query) } returns searchFlow

        viewModel.notes.test {
            // StateFlow akan memancarkan initialValue (emptyList) terlebih dahulu saat disubscribe
            assertEquals(emptyList(), awaitItem())

            viewModel.searchNotes(query)
            
            // Kemudian memancarkan hasil pencarian
            assertEquals(searchResults, awaitItem())
        }
    }
}
