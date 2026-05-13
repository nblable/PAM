package com.example.myfirstkmpapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myfirstkmpapp.data.DatabaseDriverFactory
import com.example.myfirstkmpapp.di.appModule
import org.koin.core.context.startKoin

fun main() = application {
    val driverFactory = DatabaseDriverFactory()
    startKoin {
        modules(appModule(driverFactory))
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyFirstKMPApp",
    ) {
        App(driverFactory)
    }
}