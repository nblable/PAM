package com.example.myfirstkmpapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmpapp.model.NutritionInfo
import com.example.myfirstkmpapp.repository.AIRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NutritionUiState(
    val nutritionInfo: NutritionInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class NutritionViewModel(private val repository: AIRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(NutritionUiState())
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    fun analyzeFood(foodName: String) {
        if (foodName.isBlank()) return

        _uiState.update { it.copy(isLoading = true, error = null) }
        
        viewModelScope.launch {
            repository.analyzeFood(foodName)
                .onSuccess { info ->
                    _uiState.update { it.copy(
                        nutritionInfo = info,
                        isLoading = false,
                        error = null
                    ) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = error.message ?: "Terjadi kesalahan saat menganalisis makanan",
                    ) }
                }
        }
    }
}
