package com.example.myfirstkmpapp.platform

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
}