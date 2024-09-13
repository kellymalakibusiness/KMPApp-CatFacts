package com.malakiapps.catfacts.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.app_name
import catfacts.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FactsTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface
        ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(start = 16.dp)
                )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {

                },
            ){
                Icon(painterResource(Res.drawable.settings), contentDescription = "Settings")
            }
        }
    }
}