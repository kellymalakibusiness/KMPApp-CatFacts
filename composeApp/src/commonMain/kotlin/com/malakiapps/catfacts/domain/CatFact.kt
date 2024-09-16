package com.malakiapps.catfacts.domain

data class CatFact(
    val id: String,
    val isHorizontalCard: Boolean,
    val image: String,
    val text: String,
    val liked: Boolean,
    val disliked: Boolean,
    val downloaded: Boolean
)
