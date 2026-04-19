package com.example.myfirstkmpapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myfirstkmpapp.data.HttpClientFactory
import com.example.myfirstkmpapp.data.NewsArticle
import com.example.myfirstkmpapp.data.NewsRepository
import com.example.myfirstkmpapp.ui.NewsDetailScreen
import com.example.myfirstkmpapp.ui.NewsScreen
import com.example.myfirstkmpapp.ui.NewsViewModel

// Definisi Tema Warna Pink
val PinkPrimary = Color(0xFFE91E63)      // Pink tegas
val PinkSecondary = Color(0xFFF48FB1)    // Pink lembut
val PinkBackground = Color(0xFFFCE4EC)   // Latar belakang sangat muda
val PinkSurface = Color(0xFFF8BBD0)      // Warna kartu/surface

private val PinkColorScheme = lightColorScheme(
    primary = PinkPrimary,
    secondary = PinkSecondary,
    background = PinkBackground,
    surfaceVariant = PinkSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurfaceVariant = Color.Black
)

@Composable
fun App() {
    MaterialTheme(colorScheme = PinkColorScheme) {

        val client = remember { HttpClientFactory.create() }
        val repository = remember { NewsRepository(client) }
        val viewModel = viewModel { NewsViewModel(repository) }

        var selectedArticle by remember { mutableStateOf<NewsArticle?>(null) }

        if (selectedArticle == null) {
            NewsScreen(
                viewModel = viewModel,
                onNavigateToDetail = { article ->
                    selectedArticle = article
                }
            )
        } else {
            NewsDetailScreen(
                article = selectedArticle!!,
                onBack = { selectedArticle = null }
            )
        }
    }
}