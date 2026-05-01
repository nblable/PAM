package com.example.myfirstkmpapp.viewmodel

import com.example.myfirstkmpapp.repository.AIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel(private val repository: AIRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun sendMessage(text: String) {
        val userMessage = ChatMessage(text, true)
        _uiState.update { it.copy(
            messages = it.messages + userMessage,
            isLoading = true,
            error = null
        ) }

        viewModelScope.launch {
            repository.analyzeFood(text)
                .onSuccess { info ->
                    val aiMessage = ChatMessage(
                        text = "Nutrition Info for ${info.name}:\n" +
                               "Calories: ${info.calories} kcal\n" +
                               "Protein: ${info.protein}g\n" +
                               "Carbs: ${info.carbs}g\n" +
                               "Fat: ${info.fat}g\n" +
                               "Tips: ${info.healthTips.joinToString(", ")}",
                        isUser = false
                    )
                    _uiState.update { it.copy(
                        messages = it.messages + aiMessage,
                        isLoading = false
                    ) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = error.message ?: "Unknown error"
                    ) }
                }
        }
    }
}
