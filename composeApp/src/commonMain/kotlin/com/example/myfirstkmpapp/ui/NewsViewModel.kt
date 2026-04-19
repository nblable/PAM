package com.example.myfirstkmpapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmpapp.data.NewsArticle
import com.example.myfirstkmpapp.data.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<NewsArticle>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<NewsArticle>>> = _uiState.asStateFlow()

    // Menggunakan String (link berita) sebagai ID unik untuk bookmark
    private val _bookmarkedUrls = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedUrls: StateFlow<Set<String>> = _bookmarkedUrls.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getNews()
                .onSuccess { news ->
                    _uiState.value = UiState.Success(news)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Gagal memuat berita")
                }
        }
    }

    fun toggleBookmark(newsUrl: String) {
        val current = _bookmarkedUrls.value.toMutableSet()
        if (current.contains(newsUrl)) {
            current.remove(newsUrl)
        } else {
            current.add(newsUrl)
        }
        _bookmarkedUrls.value = current
    }
}