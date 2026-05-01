package com.example.myfirstkmpapp.model

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<Content>
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String = "user"
)

@Serializable
data class Part(
    val text: String
)

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>? = null,
    val error: GeminiError? = null
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String? = null
)

@Serializable
data class GeminiError(
    val code: Int,
    val message: String,
    val status: String
)

// Model khusus untuk fitur Nutrition Analysis
@Serializable
data class NutritionInfo(
    val name: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val healthTips: List<String>
)