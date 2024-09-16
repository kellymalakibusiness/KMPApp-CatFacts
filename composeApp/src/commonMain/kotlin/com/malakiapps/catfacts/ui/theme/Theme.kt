package com.malakiapps.catfacts.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.gravitas_one_regular
import catfacts.composeapp.generated.resources.inria_sans_regular
import catfacts.composeapp.generated.resources.playpen_sans_medium
import org.jetbrains.compose.resources.Font

//Color
val lightScheme = Colors(
    primary = Color(0xFF6C538B),
    onPrimary = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFFE8E0EB),
    secondary = Color(0xFF256489),
    onSecondary = Color(0xFFFFFFFF),
    secondaryVariant = Color(0xFFC9E6FF),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    background = Color(0xFFFFF7FF),
    onBackground = Color(0xFF1D1A20),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF374957),
    isLight = true
)

val darkScheme = Colors(
    primary = Color(0xFFD8BAFA),
    onPrimary = Color(0xFF3C245A),
    primaryVariant = Color(0xFF543B72),
    secondary = Color(0xFF95CDF7),
    onSecondary = Color(0xFF00344E),
    secondaryVariant = Color(0xFF004C6E),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    background = Color(0xFF151218),
    onBackground = Color(0xFFE8E0E8),
    surface = Color(0xFF151218),
    onSurface = Color(0xFFE8E0E8),
    isLight = false
)

@Composable
fun CatFactsMaterialTheme(appBody: @Composable () -> Unit) {
    MaterialTheme(
        colors = if(isSystemInDarkTheme()){
            darkScheme
        } else {
            lightScheme
        },
        typography = MaterialTheme.typography.copy(
            h1 = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(
                        resource = Res.font.gravitas_one_regular
                    )
                )
            ),
            body1 = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(
                    Font(
                        resource = Res.font.inria_sans_regular
                    )
                )
            ),
            button = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(
                        resource = Res.font.playpen_sans_medium
                    )
                )
            )
        ),
        shapes = Shapes(
            small = RoundedCornerShape(14.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(0.dp)
        )
    ){
        SetStatusBarColor(MaterialTheme.colors.surface, darkIcons = !isSystemInDarkTheme())
        appBody()
    }
}
