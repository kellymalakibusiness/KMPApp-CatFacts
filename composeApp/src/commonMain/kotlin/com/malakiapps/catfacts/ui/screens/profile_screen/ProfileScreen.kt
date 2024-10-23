package com.malakiapps.catfacts.ui.screens.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.user_profile
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.ProfileViewModel
import com.malakiapps.catfacts.ui.screens.SupportedScreens
import com.malakiapps.catfacts.ui.screens.main_screen.LoadingImage
import com.malakiapps.catfacts.ui.screens.main_screen.getLoadingHeight
import com.malakiapps.catfacts.ui.screens.main_screen.getLoadingWidth
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val profileViewModel: ProfileViewModel = koinInject()
    val profileImage by profileViewModel.image.collectAsState()
    val name by profileViewModel.name.collectAsState()
    val factsSummary by profileViewModel.factsSummary.collectAsState()
    BackdropScaffold(
        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerElevation = 10.dp,
        modifier = modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        appBar = {},
        backLayerContent = {
            BackLayerContent(
                image = profileImage,
                name = name,
                liked = factsSummary?.likedCount,
                disliked = factsSummary?.dislikedCount,
                saved = factsSummary?.savedCount
            )
        },
        frontLayerContent = {
            FrontLayerContent(
                savedCatFacts = factsSummary?.savedFacts,
                onSavedClick = { index ->
                    navHostController.navigate(
                        route = "${SupportedScreens.SAVED_FACTS.destination}/$index"
                    )

                },
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
private fun FrontLayerContent(savedCatFacts: List<CatFact>?, onSavedClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            "Saved Facts",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primary
        )

        savedCatFacts?.let { foundFacts ->
            if (foundFacts.isEmpty()){
                Text(
                    "No Fact found. Saved Facts would be displayed here",
                    style = MaterialTheme.typography.body1
                )
            } else {
                //Grid goes here
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    itemsIndexed(foundFacts){ index, item ->
                        if(item.isHorizontalCard){
                            HorizontalSummaryFact(
                                catFact = item,
                                index = index,
                                onClick = onSavedClick,
                            )
                        } else {
                            VerticalSummaryFact(
                                catFact = item,
                                index = index,
                                onClick = onSavedClick
                            )
                        }
                    }
                }
            }
        } ?: run {
            //Loading
            Text(
                "Loading...",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
private fun BackLayerContent(
    image: ByteArray?,
    name: String,
    liked: Int?,
    disliked: Int?,
    saved: Int?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(18.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 18.dp
            )
        ) {
            ImageColumn(
                image = image,
                name = name
            )
            Spacer(modifier = Modifier.weight(1f))
            SummaryRow(
                liked = liked,
                disliked = disliked,
                saved = saved,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ImageColumn(image: ByteArray?, name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = if (image != null) {
            rememberAsyncImagePainter(model = image)
        } else {
            painterResource(Res.drawable.user_profile)
        }
        Image(
            painter = painter,
            contentDescription = "User profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
        )
        Text(
            name,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun SummaryRow(liked: Int?, disliked: Int?, saved: Int?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SummaryColumnInstance(
            label = "liked",
            value = liked
        )
        SummaryColumnInstance(
            label = "disliked",
            value = disliked
        )
        SummaryColumnInstance(
            label = "saved",
            value = saved
        )
    }
}

@Composable
private fun SummaryColumnInstance(label: String, value: Int?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value?.toString() ?: "--",
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp
        )
        Text(
            label,
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp
        )
    }
}

@Composable
fun VerticalSummaryFact(
    catFact: CatFact,
    index: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick(index) },
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
                        .height(45.dp)
                        .clip(MaterialTheme.shapes.small)
                    ,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    loading = {
                        LoadingImage(
                            modifier = Modifier
                                .size(catFact.getLoadingWidth().dp, 77.dp)
                                .height(45.dp),

                            shape = MaterialTheme.shapes.medium
                        )
                    }
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    catFact.text,
                    style = MaterialTheme.typography.body1,
                    fontSize = 9.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                    )
            }
        }
    }
}

@Composable
private fun HorizontalSummaryFact(
    catFact: CatFact,
    index: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick(index) },
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
                        .width(67.dp)
                        .clip(MaterialTheme.shapes.small)
                    ,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    loading = {
                        LoadingImage(
                            modifier = Modifier
                                .size(67.dp, catFact.getLoadingHeight().dp),

                            shape = MaterialTheme.shapes.medium
                        )
                    }
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = catFact.text,
                    style = MaterialTheme.typography.body1,
                    fontSize = 9.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5
                    )
            }
        }
    }
}