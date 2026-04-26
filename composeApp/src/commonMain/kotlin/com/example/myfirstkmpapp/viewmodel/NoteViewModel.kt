package com.example.myfirstkmpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmpapp.data.NoteRepository
import com.example.myfirstkmpapp.data.SettingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NoteRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val isDarkMode = settingsRepository.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val isSortByNewest = settingsRepository.isSortByNewest
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    // Menggabungkan Data Catatan + Fitur Pencarian + Fitur Filter (Sort)
    val notes = combine(_searchQuery, settingsRepository.isSortByNewest) { query, isNewest ->
        Pair(query, isNewest)
    }.flatMapLatest { (query, isNewest) ->
        val sourceFlow = if (query.isBlank()) repository.getNotes() else repository.searchNotes(query)

        // Lakukan sorting berdasarkan pilihan Filter
        sourceFlow.map { list ->
            if (isNewest) {
                list.sortedByDescending { it.createdAt } // Paling baru di atas
            } else {
                list.sortedBy { it.createdAt } // Paling lama di atas
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun setSortOrder(isNewest: Boolean) {
        viewModelScope.launch { settingsRepository.setSortOrder(isNewest) }
    }

    fun addOrUpdateNote(id: Long?, title: String, content: String) {
        viewModelScope.launch {
            if (id == null) repository.insert(title, content)
            else repository.update(id, title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { repository.delete(id) }
    }

    fun toggleTheme() {
        viewModelScope.launch { settingsRepository.toggleTheme() }
    }
}