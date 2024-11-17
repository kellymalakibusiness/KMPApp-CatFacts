package com.malakiapps.catfacts.ui.screens.saved_facts_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.check
import catfacts.composeapp.generated.resources.delete
import catfacts.composeapp.generated.resources.download
import catfacts.composeapp.generated.resources.icn_delete
import catfacts.composeapp.generated.resources.lbl_saved_facts
import catfacts.composeapp.generated.resources.txt_all_facts_deleted
import coil3.compose.SubcomposeAsyncImage
import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.SavedFactsViewModel
import com.malakiapps.catfacts.ui.screens.common.TopBarOnlyScaffold
import com.malakiapps.catfacts.ui.screens.main_screen.LoadingImage
import com.malakiapps.catfacts.ui.screens.main_screen.getLoadingHeight
import com.malakiapps.catfacts.ui.screens.main_screen.getLoadingWidth
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun SavedFactsScreen(navHostController: NavHostController, itemIndex: Int, modifier: Modifier = Modifier) {
    val savedFactsViewModel: SavedFactsViewModel = koinInject()
    val lazyListState = rememberLazyListState()
    val savedFacts by savedFactsViewModel.savedFacts.collectAsState()

    LaunchedEffect(key1 = savedFacts.isNotEmpty()){
        if(savedFacts.isNotEmpty()){
            lazyListState.scrollToItem(
                index = itemIndex
            )
        }
    }

    TopBarOnlyScaffold(
        title = stringResource(Res.string.lbl_saved_facts),
        onBackPress = {
            navHostController.navigateUp()
        },
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) { paddingValues ->
        if (savedFacts.isEmpty()){
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)){
                Text(
                    stringResource(Res.string.txt_all_facts_deleted),
                    style = MaterialTheme.typography.body1
                )
            }
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(
                    items = savedFacts
                ) { item ->
                    if(item.isHorizontalCard){
                        HorizontalSavedFact(
                            catFact = item,
                            onDelete = {
                                savedFactsViewModel.onDeleteFact(item)
                            }
                        )
                    } else {
                        VerticalSavedFact(
                            catFact = item,
                            onDelete = {
                                savedFactsViewModel.onDeleteFact(item)
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun VerticalSavedFact(
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
        modifier = modifier.fillMaxWidth(),
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
            Image(painterResource(Res.drawable.delete), contentDescription = stringResource(Res.string.icn_delete))
        }
    }
}