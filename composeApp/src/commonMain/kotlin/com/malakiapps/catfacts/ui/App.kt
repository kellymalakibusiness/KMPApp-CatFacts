package com.malakiapps.catfacts.ui

import androidx.compose.runtime.*
import com.malakiapps.catfacts.common.di.setImageLoaderBuilder
import com.malakiapps.catfacts.domain.ImageSelector
import com.malakiapps.catfacts.ui.screens.CatFactsMain
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.malakiapps.catfacts.ui.theme.CatFactsMaterialTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    CatFactsMaterialTheme {
        KoinContext {
            //Initialize image selector to create the singleton object
            koinInject<ImageSelector>()
            setImageLoaderBuilder()
            CatFactsMain()
        }
    }
}