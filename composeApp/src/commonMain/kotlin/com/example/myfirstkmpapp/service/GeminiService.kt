package com.example.myfirstkmpapp.service

import com.example.myfirstkmpapp.config.ApiConfig
import com.example.myfirstkmpapp.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

sealed class AIError(override val message: String) : Exception(message) {
    data class RateLimited(val retryAfter: Int) : AIError("Kuota API habis. Silakan coba lagi nanti.")
    data class Unauthorized(override val message: String) : AIError(message)
    data class ServerError(override val message: String) : AIError(message)
    data class NetworkError(override val message: String) : AIError(message)
    data class ParseError(override val message: String) : AIError(message)
    data class ModelNotFound(override val message: String) : AIError(message)
}

suspend fun <T> safeAICall(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: AIError) {
        Result.failure(e)
    } catch (e: ClientRequestException) {
        when (e.response.status.value) {
            401 -> Result.failure(AIError.Unauthorized("API Key tidak valid."))
            404 -> Result.failure(AIError.ModelNotFound("Versi Model AI tidak ditemukan. Versi Flash yang kamu tuju mungkin belum rilis publik."))
            429 -> {
                val retryAfter = e.response.headers["Retry-After"]?.toIntOrNull() ?: 60
                Result.failure(AIError.RateLimited(retryAfter))
            }
            in 500..599 -> Result.failure(AIError.ServerError("Gangguan pada server AI."))
            else -> Result.failure(AIError.ServerError("Terjadi kesalahan HTTP: ${e.response.status.value}"))
        }
    } catch (e: IOException) {
        Result.failure(AIError.NetworkError("Tidak ada koneksi internet. Periksa jaringan kamu."))
    } catch (e: SerializationException) {
        Result.failure(AIError.ParseError("Gagal membaca respons dari AI. Format data tidak sesuai."))
    }
}

suspend fun <T> retryWithBackoff(
    times: Int = 3,
    initialDelay: Long = 1000,
    maxDelay: Long = 10000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            return block()
        } catch (e: Exception) {
            when {
                e is AIError.RateLimited -> {
                    if (e.retryAfter <= 10) {
                        delay(e.retryAfter * 1000L)
                    } else {
                        throw e
                    }
                }
                e is AIError.ModelNotFound -> throw e
                e is AIError.ServerError -> {
                    delay(currentDelay)
                    currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
                }
                else -> throw e
            }
        }
    }
    return block()
}

class GeminiService(private val client: HttpClient) {
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"

    private val model = "gemini-2.5-flash"

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun analyzeFood(foodName: String): Result<NutritionInfo> = safeAICall {
        retryWithBackoff {
            val prompt = """
                Kamu adalah nutritionist profesional.
                Analisis informasi gizi untuk makanan ini: $foodName
                PENTING: Respond HANYA dengan JSON murni, tanpa markdown ```json, dan tanpa text lain.
                Format JSON yang harus dipatuhi:
                {
                    "name": "nama makanan",
                    "calories": angka kalori total,
                    "protein": angka protein dalam gram,
                    "carbs": angka karbohidrat dalam gram,
                    "fat": angka lemak dalam gram,
                    "healthTips": ["tip 1", "tip 2", "tip 3"]
                }
            """.trimIndent()

            val request = GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)))))

            val response: GeminiResponse = client.post("$baseUrl/models/$model:generateContent") {
                contentType(ContentType.Application.Json)
                parameter("key", ApiConfig.geminiApiKey)
                setBody(request)
            }.body()

            response.error?.let { error ->
                val errorMessage = error.message ?: ""
                if (errorMessage.contains("quota", ignoreCase = true) || errorMessage.contains("limit", ignoreCase = true)) {
                    throw AIError.RateLimited(60)
                } else if (errorMessage.contains("not found", ignoreCase = true)) {
                    throw AIError.ModelNotFound("Versi $model belum tersedia di API publik.")
                } else {
                    throw AIError.ServerError(errorMessage)
                }
            }

            val candidates = response.candidates
            if (candidates.isNullOrEmpty()) {
                throw AIError.ParseError("Tidak ada respons dari AI. Data kosong.")
            }

            val rawText = candidates.first().content.parts.first().text

            val cleanJson = rawText
                .replace("```json", "")
                .replace("```", "")
                .trim()

            json.decodeFromString<NutritionInfo>(cleanJson)
        }
    }
}