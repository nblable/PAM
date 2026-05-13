package com.example.myfirstkmpapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.myfirstkmpapp.screens.favorites.FavoritesScreen
import com.example.myfirstkmpapp.screens.notes.NotesListScreen
import com.example.myfirstkmpapp.screens.notes.NoteDetailScreen
import com.example.myfirstkmpapp.screens.notes.AddNoteScreen
import com.example.myfirstkmpapp.screens.notes.EditNoteScreen
import com.example.myfirstkmpapp.ui.ProfileScreen
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import com.example.myfirstkmpapp.viewmodel.ProfileViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel,
    notesViewModel: NotesViewModel,
    onOpenDrawer: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNav = currentRoute in listOf(
        BottomNavItem.Notes.route,
        BottomNavItem.Favorites.route,
        BottomNavItem.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    val items = listOf(BottomNavItem.Notes, BottomNavItem.Favorites, BottomNavItem.Profile)
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.toString()) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        },
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Notes.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Notes.route) {
                NotesListScreen(navController, notesViewModel, onOpenDrawer)
            }
            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen(navController, notesViewModel, onOpenDrawer)
            }
            composable(BottomNavItem.Profile.route) {
                val uiState by profileViewModel.uiState.collectAsState()
                ProfileScreen(
                    uiState = uiState,
                    onNameChange = { profileViewModel.updateName(it) },
                    onBioChange = { profileViewModel.updateBio(it) },
                    onToggleBio = { profileViewModel.toggleBioVisibility() },
                    onToggleEdit = { profileViewModel.toggleEditMode() },
                    onToggleDarkMode = { profileViewModel.toggleDarkMode() }
                )
            }
            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                NoteDetailScreen(noteId, navController, notesViewModel)
            }
            composable(Screen.AddNote.route) { AddNoteScreen(navController) }
            composable(
                route = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                val note = notesViewModel.notes.value.find { it.id == noteId }
                EditNoteScreen(note, navController)
            }
        }
    }
}