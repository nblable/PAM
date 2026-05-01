package com.example.myfirstkmpapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstkmpapp.viewmodel.NutritionViewModel
import com.example.myfirstkmpapp.ui.components.NutritionCard
import com.example.myfirstkmpapp.ui.components.TypingIndicator
import com.example.myfirstkmpapp.repository.AIRepositoryImpl
import com.example.myfirstkmpapp.service.GeminiService
import com.example.myfirstkmpapp.service.httpClient

private val PinkPrimary = Color(0xFFE91E63)
private val PinkLight = Color(0xFFF8BBD0)
private val PinkDark = Color(0xFFC2185B)
private val SoftGray = Color(0xFFFAFAFA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen() {
    val viewModel = remember { NutritionViewModel(AIRepositoryImpl(GeminiService(httpClient))) }
    val uiState by viewModel.uiState.collectAsState()
    var foodInput by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = PinkPrimary,
            onPrimary = Color.White,
            primaryContainer = PinkLight,
            onPrimaryContainer = PinkDark,
            background = SoftGray,
            surface = Color.White
        )
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Nutrition AI Analysis",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = PinkPrimary
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(SoftGray)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cek Kandungan Gizi Makanan",
                    style = MaterialTheme.typography.titleMedium,
                    color = PinkDark,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = foodInput,
                    onValueChange = { foodInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Misal: 1 porsi Nasi Goreng Telur") },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PinkPrimary,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.analyzeFood(foodInput) },
                            enabled = foodInput.isNotBlank() && !uiState.isLoading
                        ) {
                            Icon(Icons.Default.Search, contentDescription = "Cari", tint = PinkPrimary)
                        }
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (uiState.isLoading) {
                    TypingIndicator()
                }

                uiState.error?.let { error ->
                    ErrorBanner(error)
                }

                uiState.nutritionInfo?.let { info ->
                    NutritionCard(info)
                }
            }
        }
    }
}

@Composable
fun ErrorBanner(message: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = "Error: $message",
            color = Color(0xFFC62828),
            modifier = Modifier.padding(16.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}