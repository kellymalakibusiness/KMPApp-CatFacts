package com.malakiapps.catfacts.ui.screens.language_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.check
import com.malakiapps.catfacts.domain.LanguageViewModel
import com.malakiapps.catfacts.domain.SupportedLanguages
import com.malakiapps.catfacts.ui.screens.common.TopBarOnlyScaffold
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun LanguageScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val languagesViewModel: LanguageViewModel = koinInject()
    val currentLanguage by languagesViewModel.currentLanguage.collectAsState()
    TopBarOnlyScaffold(
        title = "Select language",
        onBackPress = {
            navHostController.navigateUp()
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(
                items = SupportedLanguages.entries
            ) { language ->
                LanguageRow(
                    isCurrentLanguage = language == currentLanguage,
                    language = language,
                    onClick = { selectedLanguage ->
                        languagesViewModel.updateLanguage(selectedLanguage)
                    }
                )
            }
        }
    }
}

@Composable
private fun LanguageRow(isCurrentLanguage: Boolean, language: SupportedLanguages, onClick:(SupportedLanguages) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(language)
                }
                .padding(top = 24.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language.label,
                style = MaterialTheme.typography.body1
            )
            if (isCurrentLanguage){
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(Res.drawable.check),
                    contentDescription = "selected"
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}