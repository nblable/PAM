package com.example.myfirstkmpapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstkmpapp.platform.BatteryInfo
import com.example.myfirstkmpapp.platform.DeviceInfo
import com.example.myfirstkmpapp.viewmodel.NotesViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier,
    deviceInfo: DeviceInfo = koinInject(),
    batteryInfo: BatteryInfo = koinInject()
) {
    val theme by viewModel.theme.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()

    Column(modifier = modifier.padding(16.dp).fillMaxSize()) {
        Text("Theme", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = theme == "light",
                onClick = { viewModel.changeTheme("light") },
                label = { Text("Light") }
            )
            FilterChip(
                selected = theme == "dark",
                onClick = { viewModel.changeTheme("dark") },
                label = { Text("Dark") }
            )
        }

        Spacer(Modifier.height(16.dp))
        Text("Sort Order", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = sortOrder == "newest",
                onClick = { viewModel.changeSortOrder("newest") },
                label = { Text("Newest") }
            )
            FilterChip(
                selected = sortOrder == "oldest",
                onClick = { viewModel.changeSortOrder("oldest") },
                label = { Text("Oldest") }
            )
        }

        Spacer(Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(Modifier.height(24.dp))

        // Task 8: Platform Features
        Text("Device Information", style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Model: ${deviceInfo.deviceModel}")
                Text("OS: ${deviceInfo.osName} ${deviceInfo.osVersion}")
            }
        }

        Text("Battery Status", style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("Level: ${batteryInfo.getBatteryLevel()}%")
                Text("Status: ${if (batteryInfo.isCharging()) "Charging ⚡" else "Discharging"}")
            }
        }
    }
}