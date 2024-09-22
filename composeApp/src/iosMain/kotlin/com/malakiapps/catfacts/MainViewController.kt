package com.malakiapps.catfacts

import androidx.compose.ui.window.ComposeUIViewController
import com.malakiapps.catfacts.common.di.initKoin
import com.malakiapps.catfacts.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}