package com.example.myfirstkmpapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myfirstkmpapp.navigation.MainScreen
import com.example.myfirstkmpapp.data.DatabaseDriverFactory
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import com.example.myfirstkmpapp.viewmodel.ProfileViewModel
import com.example.myfirstkmpapp.di.appModule
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.startKoin

val PinkPrimary = Color(0xFFD81B60)
val PinkSecondary = Color(0xFFF06292)

val LightColorScheme = lightColorScheme(
    primary = PinkPrimary, background = Color(0xFFF3F2EF)
)
val DarkColorScheme = darkColorScheme(
    primary = PinkSecondary, background = Color(0xFF121212)
)

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    KoinContext {
        val profileViewModel: ProfileViewModel = koinViewModel()
        val notesViewModel: NotesViewModel = koinViewModel()
        
        val uiState by profileViewModel.uiState.collectAsState()
        SetStatusBarColor(isDarkMode = uiState.isDarkMode)

        val colorScheme = if (uiState.isDarkMode) DarkColorScheme else LightColorScheme
        val animatedBackground by animateColorAsState(targetValue = colorScheme.background, tween(500))

        MaterialTheme(colorScheme = colorScheme) {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(16.dp))
                        Text("My Notes App", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text("About App") },
                            selected = false,
                            onClick = { scope.launch { drawerState.close() } },
                            icon = { Icon(Icons.Default.Info, contentDescription = null) },
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            ) {
                Box(modifier = Modifier.fillMaxSize().background(animatedBackground)) {
                    MainScreen(navController, profileViewModel, notesViewModel, onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    })
                }
            }
        }
    }
}
