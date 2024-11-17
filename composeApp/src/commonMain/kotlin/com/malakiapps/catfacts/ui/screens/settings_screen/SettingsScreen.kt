package com.malakiapps.catfacts.ui.screens.settings_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.about
import catfacts.composeapp.generated.resources.forward
import catfacts.composeapp.generated.resources.language
import catfacts.composeapp.generated.resources.lbl_about
import catfacts.composeapp.generated.resources.lbl_languages
import catfacts.composeapp.generated.resources.lbl_settings
import catfacts.composeapp.generated.resources.lbl_user_details
import catfacts.composeapp.generated.resources.user_details
import com.malakiapps.catfacts.ui.screens.SupportedScreens
import com.malakiapps.catfacts.ui.screens.common.TopBarOnlyScaffold
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    TopBarOnlyScaffold(
        title = stringResource(Res.string.lbl_settings),
        onBackPress = {
            navHostController.navigateUp()
        },
        modifier = modifier
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            SettingsRow(
                icon = Res.drawable.user_details,
                text = stringResource(Res.string.lbl_user_details),
                onClick = {
                    navHostController.navigate(route = SupportedScreens.USER_DETAILS.destination)
                }
            )
            SettingsRow(
                icon = Res.drawable.about,
                text = stringResource(Res.string.lbl_about),
                onClick = {
                    navHostController.navigate(route = SupportedScreens.ABOUT.destination)
                }
            )
        }
    }
}

@Composable
private fun SettingsRow(icon: DrawableResource, text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                modifier = Modifier.padding(end = 8.dp),
                contentDescription = null
            )
            Text(
                text = text,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(Res.drawable.forward),
                contentDescription = null
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}