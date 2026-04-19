package com.example.myfirstkmpapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpaceflightResponse(
    val results: List<NewsArticle>
)

@Serializable
data class NewsArticle(
    val id: Int,
    val title: String,
    val summary: String,
    @SerialName("image_url") val imageUrl: String,
    val url: String
)