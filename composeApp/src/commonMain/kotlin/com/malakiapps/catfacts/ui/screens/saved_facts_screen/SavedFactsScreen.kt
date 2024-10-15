package com.malakiapps.catfacts.ui.screens.saved_facts_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.check
import catfacts.composeapp.generated.resources.delete
import catfacts.composeapp.generated.resources.download
import coil3.compose.SubcomposeAsyncImage
import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.SupportedLanguages
import com.malakiapps.catfacts.ui.screens.common.TopBarOnlyScaffold
import com.malakiapps.catfacts.ui.screens.main_screen.LoadingImage
import com.malakiapps.catfacts.ui.screens.main_screen.getLoadingHeight
import com.malakiapps.catfacts.ui.screens.main_screen.getLoadingWidth
import org.jetbrains.compose.resources.painterResource

@Composable
fun SavedFactsScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    TopBarOnlyScaffold(
        title = "Saved Facts",
        onBackPress = {
            navHostController.navigateUp()
        },
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            /*items(
                items =
            ) {

            }*/

        }
    }
}

@Composable
fun VerticalFact(
    catFact: CatFact,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ){
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Column {
                SubcomposeAsyncImage(
                    model = catFact.image,
                    modifier = Modifier
                        .height(77.dp)
                        .clip(MaterialTheme.shapes.small)
                    ,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    loading = {
                        LoadingImage(
                            modifier = Modifier
                                .size(catFact.getLoadingWidth().dp, 77.dp)
                                .height(77.dp),

                            shape = MaterialTheme.shapes.medium
                        )
                    }
                )
                Spacer(Modifier.height(12.dp))
                Text(catFact.text, style = MaterialTheme.typography.body1)
            }
            Spacer(Modifier.height(24.dp))
            SavedFactFeedbackRow(
                catFact = catFact,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun HorizontalSavedFact(
    catFact: CatFact,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ){
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row {
                SubcomposeAsyncImage(
                    model = catFact.image,
                    modifier = Modifier
                        .width(101.dp)
                        .clip(MaterialTheme.shapes.small)
                    ,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    loading = {
                        LoadingImage(
                            modifier = Modifier
                                .size(101.dp, catFact.getLoadingHeight().dp),

                            shape = MaterialTheme.shapes.medium
                        )
                    }
                )
                Spacer(Modifier.width(16.dp))
                Text(catFact.text, style = MaterialTheme.typography.body1)
            }
            Spacer(Modifier.height(24.dp))
            SavedFactFeedbackRow(
                catFact = catFact,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun SavedFactFeedbackRow(
    catFact: CatFact,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = {
                onDelete()
            },
        ){
            val resource = if (catFact.downloaded){
                Res.drawable.check
            } else {
                Res.drawable.download
            }
            Image(painterResource(Res.drawable.delete), contentDescription = "Delete button")
        }
    }
}