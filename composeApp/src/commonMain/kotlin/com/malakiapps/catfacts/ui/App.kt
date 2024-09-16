package com.malakiapps.catfacts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.malakiapps.catfacts.common.di.setImageLoaderBuilder
import com.malakiapps.catfacts.domain.CatFact
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsBottomBar
import com.malakiapps.catfacts.ui.screens.main_screen.CatFactsTopAppBar
import com.malakiapps.catfacts.ui.screens.main_screen.HorizontalFact
import com.malakiapps.catfacts.ui.screens.main_screen.MainScreenFragment
import com.malakiapps.catfacts.ui.screens.main_screen.VerticalFact
import com.malakiapps.catfacts.ui.theme.CatFactsMaterialTheme
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    CatFactsMaterialTheme {
        KoinContext {
            setImageLoaderBuilder()
            var currentFragment by remember { mutableStateOf(MainScreenFragment.Facts) }
            var facts by remember {
                mutableStateOf(
                    listOf(
                        CatFact(
                            id = "1",
                            isHorizontalCard = true,
                            image = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
                            text = "Studies now show that the allergen in cats is related to their scent glands. Cats have scent glands on their faces and at the base of their tails. Entire male cats generate the most scent. If this secretion from the scent glands is the allergen, allergic people should tolerate spayed female cats the best.",
                            liked = false,
                            disliked = false,
                            downloaded = false
                        ),
                        CatFact(
                            id = "2",
                            isHorizontalCard = false,
                            image = "https://cdn2.thecatapi.com/images/2oc.jpg",
                            text = "A cats purr may be a form of self-healing, as it can be a sign of nervousness as well as contentment. Similarly, the frequency of a domestic cats purr is the same at which muscles and bones repair themselves.",
                            liked = true,
                            disliked = true,
                            downloaded = true
                        )
                    )
                )
            }
            Scaffold(
                backgroundColor = MaterialTheme.colors.background,
                topBar = {
                    CatFactsTopAppBar(onSettingsClick = {
                        //Todo implement on open settings
                    })
                },
                bottomBar = {
                    CatFactsBottomBar(
                        currentFragment = currentFragment,
                        onFragmentChange = { newFragment ->
                            currentFragment = newFragment
                        }
                    )
                }
            ){ innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(items = facts, key = { _, item ->
                        item.id
                    }){ index, fact ->
                        if(fact.isHorizontalCard){
                            HorizontalFact(
                                catFact = fact,
                                onLike = {
                                   //Temp implementation
                                    facts = listOf(
                                        fact.copy(liked = it)
                                    )
                                },
                                onDislike = {
                                    //Temp implementation
                                    facts = listOf(
                                        fact.copy(disliked = it)
                                    )
                                },
                                download = {
                                    //Temp implementation
                                    facts = listOf(
                                        fact.copy(downloaded = it)
                                    )
                                }
                            )
                        } else {
                            VerticalFact(
                                catFact = fact,
                                onLike = {
                                    //Temp implementation
                                    facts = listOf(
                                        fact.copy(liked = it)
                                    )
                                },
                                onDislike = {
                                    //Temp implementation
                                    facts = listOf(
                                        fact.copy(disliked = it)
                                    )
                                },
                                download = {
                                    //Temp implementation
                                    facts = listOf(
                                        fact.copy(downloaded = it)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}