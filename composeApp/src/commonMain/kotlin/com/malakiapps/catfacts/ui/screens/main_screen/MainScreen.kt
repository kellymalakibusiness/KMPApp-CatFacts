package com.malakiapps.catfacts.ui.screens.main_screen

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import catfacts.composeapp.generated.resources.Res
import catfacts.composeapp.generated.resources.app_name
import catfacts.composeapp.generated.resources.btn_dislike
import catfacts.composeapp.generated.resources.btn_download
import catfacts.composeapp.generated.resources.btn_like
import catfacts.composeapp.generated.resources.btn_retry
import catfacts.composeapp.generated.resources.check
import catfacts.composeapp.generated.resources.dislike_down
import catfacts.composeapp.generated.resources.dislike_up
import catfacts.composeapp.generated.resources.download
import catfacts.composeapp.generated.resources.error_body_default
import catfacts.composeapp.generated.resources.error_title
import catfacts.composeapp.generated.resources.facts
import catfacts.composeapp.generated.resources.facts_down
import catfacts.composeapp.generated.resources.facts_up
import catfacts.composeapp.generated.resources.icn_settings
import catfacts.composeapp.generated.resources.like_down
import catfacts.composeapp.generated.resources.like_up
import catfacts.composeapp.generated.resources.line
import catfacts.composeapp.generated.resources.profile
import catfacts.composeapp.generated.resources.settings
import catfacts.composeapp.generated.resources.user_down
import catfacts.composeapp.generated.resources.user_up
import coil3.compose.SubcomposeAsyncImage
import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.MainViewModel
import com.malakiapps.catfacts.ui.screens.SupportedScreens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val mainViewModel: MainViewModel = koinInject()
    val facts by mainViewModel.catFacts.collectAsState()
    val listState = rememberLazyListState()
    var fetchingState by remember { mutableStateOf(FactsFetchingLevels.NOT_CALLING) }
    val errorMessage by mainViewModel.errorMessage.collectAsState()
    val shouldFetchNextPage by derivedStateOf {
            listState.layoutInfo.let { layoutInfo ->
                val remainingItems = layoutInfo.totalItemsCount - (listState.firstVisibleItemIndex + layoutInfo.visibleItemsInfo.size)
                if(remainingItems < 3 && layoutInfo.totalItemsCount != 0){
                    fetchingState == FactsFetchingLevels.NOT_CALLING
                    } else {
                        false
                }
            }
    }

    LaunchedEffect(shouldFetchNextPage) {
        if (shouldFetchNextPage){
            fetchingState = FactsFetchingLevels.CALLING
            mainViewModel.refreshFacts()
        }
    }

    LaunchedEffect(listState.layoutInfo.totalItemsCount){
        val remainingItems = listState.layoutInfo.totalItemsCount - (listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size)
        if(remainingItems > 5){
            fetchingState = FactsFetchingLevels.NOT_CALLING
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(facts.isEmpty()){
            repeat(6){
                item {
                    LoadingCard()
                }
            }
        } else {
            itemsIndexed(items = facts/*, key = */){ _, fact ->
                if(fact == null){
                    LoadingCard()
                } else {
                    if(fact.isHorizontalCard){
                        HorizontalFact(
                            catFact = fact,
                            onLike = {
                                mainViewModel.onFactLike(fact.text, it)
                            },
                            onDislike = {
                                mainViewModel.onFactDisLike(fact.text, it)
                            },
                            download = {
                                mainViewModel.onFactDownload(fact, it)
                            }
                        )
                    } else {
                        VerticalFact(
                            catFact = fact,
                            onLike = {
                                mainViewModel.onFactLike(fact.text, it)
                            },
                            onDislike = {
                                mainViewModel.onFactDisLike(fact.text, it)
                            },
                            download = {
                                mainViewModel.onFactDownload(fact, it)
                            }
                        )
                    }
                }
            }
        }
    }

    if(errorMessage != null){
        AlertDialog(
            title = {
                Text(
                    stringResource(Res.string.error_title),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary
                )
            },
            text = {
                Text(
                    errorMessage ?: stringResource(Res.string.error_body_default),
                    style = MaterialTheme.typography.body1
                )
            },
            onDismissRequest = {
                mainViewModel.onDismissErrorMessage()
            },
            buttons = {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = stringResource(Res.string.btn_retry),
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.clickable {
                            mainViewModel.onDismissErrorMessage()
                            mainViewModel.refreshFacts()
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun CatFactsTopAppBar(onSettingsClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
    ) {
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
            ) {
                Icon(
                    painterResource(Res.drawable.settings),
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = stringResource(Res.string.icn_settings)
                )
            }
        }
    }
}

@Composable
fun CatFactsBottomBar(
    currentScreen: String?,
    onFragmentChange: (SupportedScreens) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.primary,
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        BottomNavigationItem(
            selected = currentScreen == SupportedScreens.FACTS.destination,
            onClick = {
                onFragmentChange(SupportedScreens.FACTS)
            },
            icon = {
                val icon = if (currentScreen == SupportedScreens.FACTS.destination) {
                    Res.drawable.facts_down
                } else {
                    Res.drawable.facts_up
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(icon), null)
                    if (currentScreen == SupportedScreens.FACTS.destination) {
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
            selected = currentScreen == SupportedScreens.PROFILE.destination,
            onClick = {
                onFragmentChange(SupportedScreens.PROFILE)
            },
            icon = {
                val icon = if (currentScreen == SupportedScreens.PROFILE.destination) {
                    Res.drawable.user_down
                } else {
                    Res.drawable.user_up
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(icon), null)
                    if (currentScreen == SupportedScreens.PROFILE.destination) {
                        Text(
                            stringResource(Res.string.profile),
                            style = MaterialTheme.typography.button
                        )
                        Image(painterResource(Res.drawable.line), null)
                    }
                }
            }
        )
    }
}

@Composable
fun HorizontalFact(
    catFact: CatFact,
    onLike: (Boolean) -> Unit,
    onDislike: (Boolean) -> Unit,
    download: (Boolean) -> Unit,
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
            FeedbackRow(
                catFact = catFact,
                onLike = onLike,
                onDislike = onDislike,
                download = download
            )
        }
    }
}

@Composable
fun VerticalFact(
    catFact: CatFact,
    onLike: (Boolean) -> Unit,
    onDislike: (Boolean) -> Unit,
    download: (Boolean) -> Unit,
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
            FeedbackRow(
                catFact = catFact,
                onLike = onLike,
                onDislike = onDislike,
                download = download
            )
        }
    }
}

@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            LoadingImage(
                modifier = Modifier.size(101.dp, 138.dp),
                shape = MaterialTheme.shapes.medium
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                repeat(5){
                    LoadingImage(
                        modifier = Modifier.size(220.dp, 18.dp).padding(bottom = 7.dp),
                        shape = RoundedCornerShape(0.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedbackRow(
    catFact: CatFact,
    onLike: (Boolean) -> Unit,
    onDislike: (Boolean) -> Unit,
    download: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                onLike(!catFact.liked)
            },
            modifier = Modifier.padding(end = 24.dp)
        ){
            val resource = if (catFact.liked){
                Res.drawable.like_down
            } else {
                Res.drawable.like_up
            }
            Image(painterResource(resource), contentDescription = stringResource(Res.string.btn_like))
        }
        IconButton(
            onClick = {
                onDislike(!catFact.disliked)
            },
        ){
            val resource = if (catFact.disliked){
                Res.drawable.dislike_down
            } else {
                Res.drawable.dislike_up
            }
            Image(painterResource(resource), contentDescription = stringResource(Res.string.btn_dislike))
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                download(!catFact.downloaded)
            },
        ){
            val resource = if (catFact.downloaded){
                Res.drawable.check
            } else {
                Res.drawable.download
            }
            Image(painterResource(resource), contentDescription = stringResource(Res.string.btn_download))
        }
    }
}

@Composable
fun LoadingImage(shape: Shape, modifier: Modifier){
    Box(
        modifier = modifier.loadingEffect(shape = shape)
    )
}

private fun Modifier.loadingEffect(shape: Shape): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue =   2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000
            )
        )
    )

    background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colors.primaryVariant,
                Color(0xFF8F8B8B),
                MaterialTheme.colors.primaryVariant
            ),
            startX = startOffsetX,
            endX = startOffsetX + size.width
        ),
        shape = shape
    )
        .onGloballyPositioned {
            size = it.size
        }
}

fun CatFact.getLoadingWidth(): Double {
    val width = imageWidth ?: 1
    val height = imageHeight ?: 1

    return width / (height * 77.0)
}

fun CatFact.getLoadingHeight(): Double {
    val width = imageWidth ?: 1
    val height = imageHeight ?: 1

    return height / (width * 101.0)
}

private enum class FactsFetchingLevels{
    NOT_CALLING,
    CALLING
}