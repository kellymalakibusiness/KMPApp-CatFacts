package com.malakiapps.catfacts.ui.screens.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Settings Coming soon!", style = MaterialTheme.typography.body1)
    }
}