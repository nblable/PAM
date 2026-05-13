package com.example.myfirstkmpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.runtime.remember
import com.example.myfirstkmpapp.data.DatabaseDriverFactory
import com.example.myfirstkmpapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val driverFactory = DatabaseDriverFactory(this)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule(driverFactory))
        }

        setContent {
            App(driverFactory)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val driverFactory = DatabaseDriverFactory(androidx.compose.ui.platform.LocalContext.current)
    App(driverFactory)
}