package com.malakiapps.catfacts.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.back_button
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBarOnlyScaffold(title: String, onBackPress: () -> Unit, backgroundColor: Color = MaterialTheme.colors.surface, modifier: Modifier = Modifier, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        modifier = modifier.statusBarsPadding(),
        backgroundColor = backgroundColor,
        topBar = {
            LabelTopAppBar(
                label = title,
                onBackPress = onBackPress
                )
        }
    ){ innerPadding ->
        content(innerPadding)
    }
}


@Composable
fun LabelTopAppBar(label: String, onBackPress: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            IconButton(
                onClick = onBackPress,
                modifier = Modifier.align(Alignment.CenterStart)
            ){
                Icon(
                    painter = painterResource(Res.drawable.back_button),
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = "Go back"
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}