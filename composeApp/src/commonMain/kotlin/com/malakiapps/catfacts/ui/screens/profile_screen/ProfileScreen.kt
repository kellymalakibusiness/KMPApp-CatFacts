package com.malakiapps.catfacts.ui.screens.profile_screen

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.malakiapps.catfacts.domain.MainViewModel

@Composable
fun ProfileScreen(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    Text("Profile Coming soon!", style = MaterialTheme.typography.body1)
}