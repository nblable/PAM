package com.example.myfirstkmpapp.di

import com.example.myfirstkmpapp.data.DatabaseDriverFactory
import com.example.myfirstkmpapp.data.NoteRepository
import com.example.myfirstkmpapp.data.SqlDelightNoteRepository
import com.example.myfirstkmpapp.db.MyDatabase
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import com.example.myfirstkmpapp.viewmodel.ProfileViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

fun dataModule(driverFactory: DatabaseDriverFactory) = module {
    single { 
        MyDatabase(
            driver = driverFactory.createDriver()
        )
    }
    single<NoteRepository> { SqlDelightNoteRepository(get()) }
}

val viewModelModule = module {
    viewModel { NotesViewModel(get()) }
    viewModel { ProfileViewModel() }
}

fun appModule(driverFactory: DatabaseDriverFactory) = listOf(dataModule(driverFactory), viewModelModule)
