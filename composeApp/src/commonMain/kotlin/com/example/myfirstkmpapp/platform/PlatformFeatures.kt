package com.example.myfirstkmpapp.platform

import kotlinx.coroutines.flow.StateFlow

// Device Info
expect class DeviceInfo() {
    val osName: String
    val osVersion: String
    val deviceModel: String
    fun getSummary(): String
}

// Network Monitor
expect class NetworkMonitor {
    val isConnected: StateFlow<Boolean>
}

// Battery Info (Bonus)
expect class BatteryInfo {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}