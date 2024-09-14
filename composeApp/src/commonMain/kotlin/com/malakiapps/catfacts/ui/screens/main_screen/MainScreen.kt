package com.malakiapps.catfacts.ui.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
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
import catfacts.composeapp.generated.resources.facts
import catfacts.composeapp.generated.resources.facts_down
import catfacts.composeapp.generated.resources.facts_up
import catfacts.composeapp.generated.resources.line
import catfacts.composeapp.generated.resources.profile
import catfacts.composeapp.generated.resources.settings
import catfacts.composeapp.generated.resources.user_down
import catfacts.composeapp.generated.resources.user_up
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CatFactsTopAppBar(onSettingsClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.systemBarsPadding(),
        backgroundColor = MaterialTheme.colors.surface,
        ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colors.onSurface
                )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    onSettingsClick()
                },
            ){
                Icon(painterResource(Res.drawable.settings), tint = MaterialTheme.colors.onSurface, contentDescription = "Settings")
            }
        }
    }
}

@Composable
fun CatFactsBottomBar(currentFragment: MainScreenFragment, onFragmentChange: (MainScreenFragment)-> Unit, modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.primary,
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        BottomNavigationItem(
            selected = currentFragment == MainScreenFragment.Facts,
            onClick = {
                onFragmentChange(MainScreenFragment.Facts)
            },
            icon = {
                val icon = when(currentFragment){
                    MainScreenFragment.Facts -> Res.drawable.facts_down
                    MainScreenFragment.Profile -> Res.drawable.facts_up
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(icon), null)
                    if (currentFragment == MainScreenFragment.Facts) {
                        Text(
                            stringResource(Res.string.facts),
                            style = MaterialTheme.typography.button
                        )
                        Image(painterResource(Res.drawable.line), null)
                    }
                }
            }
        )

        BottomNavigationItem(
            selected = currentFragment == MainScreenFragment.Profile,
            onClick = {
                onFragmentChange(MainScreenFragment.Profile)
            },
            icon = {
                val icon = when(currentFragment){
                    MainScreenFragment.Profile -> Res.drawable.user_down
                    MainScreenFragment.Facts -> Res.drawable.user_up
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(icon), null)
                    if(currentFragment == MainScreenFragment.Profile){
                        Text(stringResource(Res.string.profile), style = MaterialTheme.typography.button)
                        Image(painterResource(Res.drawable.line), null)
                    }
                }
            }
        )
    }
}