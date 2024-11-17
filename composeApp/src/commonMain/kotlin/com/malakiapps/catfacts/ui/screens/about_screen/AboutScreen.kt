package com.malakiapps.catfacts.ui.screens.about_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.*
import com.malakiapps.catfacts.domain.AboutViewModel
import com.malakiapps.catfacts.ui.screens.common.TopBarOnlyScaffold
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun AboutScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val aboutViewModel: AboutViewModel = koinInject()
    TopBarOnlyScaffold(
        title = stringResource(Res.string.lbl_about),
        onBackPress = {
            navHostController.navigateUp()
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                AboutEntry(
                    title = stringResource(Res.string.lbl_cat_facts_api),
                    description = stringResource(Res.string.txt_cat_facts_api_description),
                    link = stringResource(Res.string.lnk_cat_facts),
                    onClick = { link ->
                        aboutViewModel.openLink(link)
                    }
                )
            }

            item {
                AboutEntry(
                    title = stringResource(Res.string.lbl_cat_images_api),
                    description = stringResource(Res.string.txt_cat_images_description),
                    link = stringResource(Res.string.lnk_cat_images),
                    onClick = { link ->
                        aboutViewModel.openLink(link)
                    }
                )
            }

            item {
                AboutEntry(
                    title = stringResource(Res.string.lbl_developer),
                    description = stringResource(Res.string.txt_developer_description),
                    link = stringResource(Res.string.lnk_github),
                    onClick = { link ->
                        aboutViewModel.openLink(link)
                    }
                )
            }
        }
    }
}

@Composable
fun AboutEntry(title: String, description: String, link: String, onClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = link,
            style = MaterialTheme.typography.body1,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(bottom = 18.dp)
                .clickable { onClick(link) }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}