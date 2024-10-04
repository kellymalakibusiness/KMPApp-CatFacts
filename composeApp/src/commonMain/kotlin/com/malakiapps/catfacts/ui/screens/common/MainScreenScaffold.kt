package com.malakiapps.catfacts.ui.screens.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.malakiapps.catfacts.ui.screens.SupportedScreens
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsBottomBar
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsTopAppBar

@Composable
fun MainScreenScaffold(navController: NavHostController, modifier: Modifier = Modifier, innerFragment: @Composable (PaddingValues) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            CatFactsTopAppBar(
                onSettingsClick = {
                    navController.navigateSingleTopToScreen(screen = SupportedScreens.SETTINGS)
                }
            )
        },
        bottomBar = {
            CatFactsBottomBar(
                currentScreen = navBackStackEntry?.destination?.route,
                onFragmentChange = { supportedScreen ->
                    navController.navigateSingleTopToScreen(screen = supportedScreen)
                }
            )
        }
    ) { innerPadding ->
        innerFragment(innerPadding)
    }
}

private fun NavHostController.navigateSingleTopToScreen(screen: SupportedScreens, popUpToMainScreen: Boolean = false){
    navigate(screen.destination){
        launchSingleTop = true
        if (popUpToMainScreen){
            popUpTo(SupportedScreens.FACTS.destination){
                saveState = true
            }
        }
    }
}