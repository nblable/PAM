package com.example.myfirstkmpapp.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsRepository(private val client: HttpClient) {
    // Mengambil 20 berita terbaru seputar Spaceflight
    private val baseUrl = "https://api.spaceflightnewsapi.net/v4/articles/?limit=20"

    suspend fun getNews(): Result<List<NewsArticle>> {
        return try {
            val response: SpaceflightResponse = client.get(baseUrl).body()
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}