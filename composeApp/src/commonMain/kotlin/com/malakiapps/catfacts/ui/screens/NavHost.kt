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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.malakiapps.catfacts.domain.MainViewModel
import com.malakiapps.catfacts.ui.screens.about_screen.AboutScreen
import com.malakiapps.catfacts.ui.screens.common.MainScreenScaffold
import com.malakiapps.catfacts.ui.screens.language_screen.LanguageScreen
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsBottomBar
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsTopAppBar
import com.malakiapps.catfacts.ui.screens.main_screen.MainScreen
import com.malakiapps.catfacts.ui.screens.profile_screen.ProfileScreen
import com.malakiapps.catfacts.ui.screens.saved_facts_screen.SavedFactsScreen
import com.malakiapps.catfacts.ui.screens.settings_screen.SettingsScreen
import com.malakiapps.catfacts.ui.screens.user_details_screen.UserDetailsScreen
import org.koin.compose.koinInject

@Composable
fun CatFactsMain(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    CatFactsNavHost(
        navController = navController,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun CatFactsNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = SupportedScreens.FACTS.destination, modifier = modifier){
        composable(route = SupportedScreens.FACTS.destination){
            MainScreenScaffold(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            ){ innerPadding ->
                MainScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

        composable(route = SupportedScreens.PROFILE.destination){
            MainScreenScaffold(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            ){ innerPadding ->
                ProfileScreen(
                    navHostController = navController,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }

        composable(route = SupportedScreens.SETTINGS.destination){
            SettingsScreen(
                navHostController = navController,
                modifier = Modifier.fillMaxSize(),
                )
        }

        composable(route = SupportedScreens.LANGUAGE.destination){
            LanguageScreen(
                navHostController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable(route = SupportedScreens.ABOUT.destination){
            AboutScreen(
                navHostController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable(route = SupportedScreens.USER_DETAILS.destination){
            UserDetailsScreen(
                navHostController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable(
            route = "${SupportedScreens.SAVED_FACTS.destination}/{item_index}",
            arguments = listOf(
                navArgument("item_index"){
                    type = NavType.IntType
                }
            )
            ){ backStackEntry ->
            SavedFactsScreen(
                navHostController = navController,
                itemIndex = backStackEntry.arguments?.getInt("item_index") ?: 0
            )
        }
    }
}