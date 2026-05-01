package com.example.myfirstkmpapp.repository

import com.example.myfirstkmpapp.model.NutritionInfo
import com.example.myfirstkmpapp.service.GeminiService

interface AIRepository {
    suspend fun analyzeFood(foodName: String): Result<NutritionInfo>
}

class AIRepositoryImpl(private val geminiService: GeminiService) : AIRepository {
    override suspend fun analyzeFood(foodName: String): Result<NutritionInfo> {
        return geminiService.analyzeFood(foodName)
    }
}