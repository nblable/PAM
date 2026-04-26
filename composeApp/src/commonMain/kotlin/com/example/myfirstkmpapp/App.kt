package com.example.myfirstkmpapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstkmpapp.platform.NetworkMonitor
import com.example.myfirstkmpapp.screen.NoteEditorScreen
import com.example.myfirstkmpapp.screen.NotesScreen
import com.example.myfirstkmpapp.screen.SettingsScreen
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

// Custom Pink Color Palette
val PinkPrimary = Color(0xFFD81B60)
val PinkSecondary = Color(0xFFF06292)

val LightColorScheme = lightColorScheme(
    primary = PinkPrimary,
    background = Color(0xFFF3F2EF),
    surface = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = PinkSecondary,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val viewModel = koinInject<NotesViewModel>()
    val networkMonitor = koinInject<NetworkMonitor>()

    val theme by viewModel.theme.collectAsState()
    val isConnected by networkMonitor.isConnected.collectAsState(initial = true)
    val searchQuery by viewModel.searchQuery.collectAsState()

    val colorScheme = if (theme == "dark") DarkColorScheme else LightColorScheme
    val animatedBackground by animateColorAsState(targetValue = colorScheme.background, tween(500))

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var screen by remember { mutableStateOf("notes") }
    var editNoteId by remember { mutableStateOf<Long?>(null) }

    MaterialTheme(colorScheme = colorScheme) {
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
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                if (screen == "notes") {
                                    TextField(
                                        value = searchQuery,
                                        onValueChange = { viewModel.onSearchChange(it) },
                                        placeholder = { Text("Cari...", color = Color.White.copy(0.6f)) },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedTextColor = Color.White,
                                            unfocusedTextColor = Color.White,
                                            cursorColor = Color.White
                                        )
                                    )
                                } else Text(screen.uppercase(), fontWeight = FontWeight.Bold)
                            },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = null)
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = colorScheme.primary,
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White
                            )
                        )
                    },
                    bottomBar = {
                        if (screen != "editor") {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = screen == "notes",
                                    onClick = { screen = "notes" },
                                    label = { Text("Notes") },
                                    icon = { Icon(Icons.Default.Notes, null) }
                                )
                                NavigationBarItem(
                                    selected = screen == "settings",
                                    onClick = { screen = "settings" },
                                    label = { Text("Settings") },
                                    icon = { Icon(Icons.Default.Settings, null) }
                                )
                            }
                        }
                    }
                ) { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                        if (!isConnected) {
                            Box(modifier = Modifier.fillMaxWidth().background(Color.Red).padding(4.dp), contentAlignment = Alignment.Center) {
                                Text("Offline Mode", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        when (screen) {
                            "notes" -> NotesScreen(
                                viewModel = viewModel,
                                onAddClick = { editNoteId = null; screen = "editor" },
                                onEditClick = { id -> editNoteId = id; screen = "editor" }
                            )
                            "editor" -> NoteEditorScreen(
                                noteId = editNoteId,
                                viewModel = viewModel,
                                onBack = { screen = "notes" }
                            )
                            "settings" -> SettingsScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}