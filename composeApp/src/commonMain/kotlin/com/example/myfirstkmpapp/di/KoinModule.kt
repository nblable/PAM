package com.example.myfirstkmpapp.di

import com.example.myfirstkmpapp.data.repository.NoteRepository
import com.example.myfirstkmpapp.data.settings.SettingsManager
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

val appModule = module {
    single { NoteRepository(get()) }
    single { SettingsManager() }
    factory { NotesViewModel(get(), get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule, platformModule())
    }
