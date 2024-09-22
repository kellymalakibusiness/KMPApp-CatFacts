package com.malakiapps.catfacts.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.malakiapps.catfacts.domain.MainViewModel
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsBottomBar
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsTopAppBar
import com.malakiapps.catfacts.ui.screens.main_screen.MainScreen
import com.malakiapps.catfacts.ui.screens.profile_screen.ProfileScreen
import com.malakiapps.catfacts.ui.screens.settings_screen.SettingsScreen
import org.koin.compose.koinInject

@Composable
fun CatFactsMain(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val mainViewModel: MainViewModel = koinInject()

    val showScaffold = navBackStackEntry?.destination?.route.let { route ->
        (route == SupportedScreens.FACTS.destination || route == SupportedScreens.PROFILE.destination)
    }

    if(showScaffold){
        Scaffold(
            modifier = modifier.statusBarsPadding(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                CatFactsTopAppBar(onSettingsClick = {
                    navController.navigateSingleTopToScreen(screen = SupportedScreens.SETTINGS)
                })
            },
            bottomBar = {
                CatFactsBottomBar(
                    currentScreen = navBackStackEntry?.destination?.route,
                    onFragmentChange = { currentScreen ->
                        navController.navigateSingleTopToScreen(screen = currentScreen)
                    }
                )
            }
        ) { innerPadding ->
            CatFactsNavHost(
                navController = navController,
                mainViewModel = mainViewModel,
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
            )
        }
    } else {
        CatFactsNavHost(
            navController = navController,
            mainViewModel = mainViewModel,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CatFactsNavHost(navController: NavHostController, mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = SupportedScreens.FACTS.destination, modifier = modifier){
        composable(route = SupportedScreens.FACTS.destination){
            MainScreen(
                mainViewModel = mainViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = SupportedScreens.PROFILE.destination){
            ProfileScreen(modifier = Modifier.fillMaxSize())
        }

        composable(route = SupportedScreens.SETTINGS.destination){
            SettingsScreen(modifier = Modifier.fillMaxSize())
        }
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
