package com.example.myfirstkmpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfirstkmpapp.data.NoteRepository
import com.example.myfirstkmpapp.data.SettingsRepository
import com.example.myfirstkmpapp.data.local.DatabaseDriverFactory
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import com.example.myfirstkmpapp.NotesDatabase

val android.content.Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val driver = DatabaseDriverFactory(this).createDriver()
        val database = NotesDatabase(driver)

        val noteRepository = NoteRepository(database)
        val settingsRepository = SettingsRepository(dataStore)

        val viewModel = NotesViewModel(noteRepository, settingsRepository)

        setContent {
            App(viewModel)
        }
    }
}