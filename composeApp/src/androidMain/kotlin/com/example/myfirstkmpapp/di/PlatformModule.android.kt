package com.example.myfirstkmpapp.di

import com.example.myfirstkmpapp.db.MyDatabase
import com.example.myfirstkmpapp.data.local.DatabaseDriverFactory
import com.example.myfirstkmpapp.platform.BatteryInfo
import com.example.myfirstkmpapp.platform.DeviceInfo
import com.example.myfirstkmpapp.platform.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseDriverFactory(androidContext()) }
    single { MyDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single { DeviceInfo() }
    single { NetworkMonitor(androidContext()) }
    single { BatteryInfo(androidContext()) }
}