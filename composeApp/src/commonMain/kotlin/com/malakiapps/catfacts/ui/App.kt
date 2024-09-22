package com.malakiapps.catfacts.ui

import androidx.compose.runtime.*
import com.malakiapps.catfacts.common.di.setImageLoaderBuilder
import com.malakiapps.catfacts.ui.screens.CatFactsMain
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.malakiapps.catfacts.ui.theme.CatFactsMaterialTheme
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    CatFactsMaterialTheme {
        KoinContext {
            setImageLoaderBuilder()
            CatFactsMain()
        }
    }
}