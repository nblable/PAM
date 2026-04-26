package com.example.myfirstkmpapp.viewmodel

import com.example.myfirstkmpapp.db.Note
import com.example.myfirstkmpapp.data.repository.NoteRepository
import com.example.myfirstkmpapp.data.settings.SettingsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
    private val settingsManager: SettingsManager
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sortOrder = MutableStateFlow("newest")
    val sortOrder = _sortOrder.asStateFlow()

    val theme = settingsManager.isDarkMode.map { if (it) "dark" else "light" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, "light")

    val notes: StateFlow<List<Note>> = combine(_searchQuery, _sortOrder) { query, order ->
        query to order
    }.flatMapLatest { (query, order) ->
        val dbQuery = if (query.isBlank()) "%" else "%$query%"
        if (order == "newest") repository.getNotesNewest(dbQuery)
        else repository.getNotesOldest(dbQuery)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote = _selectedNote.asStateFlow()

    fun onSearchChange(query: String) { _searchQuery.value = query }
    fun changeSortOrder(order: String) { _sortOrder.value = order }
    fun changeTheme(newTheme: String) {
        viewModelScope.launch { settingsManager.setDarkMode(newTheme == "dark") }
    }

    fun selectNote(id: Long) { _selectedNote.value = notes.value.find { it.id == id } }
    fun clearSelectedNote() { _selectedNote.value = null }
    fun addNote(t: String, c: String) = viewModelScope.launch { repository.insertNote(t, c) }
    fun updateNote(id: Long, t: String, c: String) = viewModelScope.launch { repository.updateNote(id, t, c) }
    fun deleteNote(id: Long) = viewModelScope.launch { repository.deleteNote(id) }
}
