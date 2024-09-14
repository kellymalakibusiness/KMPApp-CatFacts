package com.malakiapps.catfacts.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
actual fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val activity = LocalContext.current as? Activity
    activity?.window?.let { window ->
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = color.toArgb()

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.isAppearanceLightStatusBars = darkIcons
    }
}