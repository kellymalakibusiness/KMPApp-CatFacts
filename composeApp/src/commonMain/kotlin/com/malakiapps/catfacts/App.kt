package com.malakiapps.catfacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsBottomBar
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsTopAppBar
import com.malakiapps.catfacts.ui.screens.main_screen.MainScreenFragment
import com.malakiapps.catfacts.ui.theme.CatFactsMaterialTheme

@Composable
@Preview
fun App() {
    var currentFragment by remember { mutableStateOf(MainScreenFragment.Facts) }
    CatFactsMaterialTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                CatFactsTopAppBar(onSettingsClick = {
                    //Todo implement on open settings
                })
            },
            bottomBar = {
                CatFactsBottomBar(
                    currentFragment = currentFragment,
                    onFragmentChange = { newFragment ->
                        currentFragment = newFragment
                    }
                )
            }
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {

            }
        }
    }
}