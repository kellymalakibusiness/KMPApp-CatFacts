package com.malakiapps.catfacts

import androidx.compose.ui.window.ComposeUIViewController
import com.malakiapps.catfacts.common.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}