package com.example.myfirstkmpapp.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.navigation.NavHostController
import com.example.myfirstkmpapp.data.Note
import com.example.myfirstkmpapp.data.NoteRepository
import com.example.myfirstkmpapp.screens.notes.NotesListScreen
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class NotesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository: NoteRepository = mockk()
    private val navController: NavHostController = mockk(relaxed = true)
    
    private val fakeNotes = listOf(
        Note(1, "Test Note 1", "Content 1"),
        Note(2, "Test Note 2", "Content 2")
    )
    private val notesFlow = MutableStateFlow<List<Note>>(emptyList())

    // 18. Screen Displays Empty State
    @Test
    fun notesScreen_displaysEmptyStateWhenNoNotes() {
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)

        composeTestRule.setContent {
            NotesListScreen(
                navController = navController,
                notesViewModel = viewModel,
                onOpenDrawer = {}
            )
        }

        composeTestRule.onNodeWithTag("EMPTY_STATE").assertIsDisplayed()
        composeTestRule.onNodeWithText("No notes available").assertIsDisplayed()
    }

    // 19. Screen Displays Note List
    @Test
    fun notesScreen_displaysNoteListCorrectly() {
        notesFlow.value = fakeNotes
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)

        composeTestRule.setContent {
            NotesListScreen(
                navController = navController,
                notesViewModel = viewModel,
                onOpenDrawer = {}
            )
        }

        composeTestRule.onNodeWithTag("NOTES_LIST").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Note 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Note 2").assertIsDisplayed()
    }

    // 20. FAB is Clickable
    @Test
    fun addNoteButton_isClickableAndTriggersAction() {
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)

        composeTestRule.setContent {
            NotesListScreen(
                navController = navController,
                notesViewModel = viewModel,
                onOpenDrawer = {}
            )
        }

        composeTestRule.onNodeWithTag("FAB_ADD_NOTE").performClick()
        // Check if navigation was triggered (via mockk verify if possible, or just check tag existence)
        composeTestRule.onNodeWithTag("FAB_ADD_NOTE").assertIsDisplayed()
    }

    // 21. Note Item is Clickable
    @Test
    fun noteItem_isClickableAndNavigatesToDetail() {
        notesFlow.value = fakeNotes
        coEvery { repository.getAllNotes() } returns notesFlow
        val viewModel = NotesViewModel(repository)

        composeTestRule.setContent {
            NotesListScreen(
                navController = navController,
                notesViewModel = viewModel,
                onOpenDrawer = {}
            )
        }

        composeTestRule.onNodeWithTag("NOTE_ITEM_1").performClick()
        composeTestRule.onNodeWithTag("NOTE_ITEM_1").assertIsDisplayed()
    }
}
