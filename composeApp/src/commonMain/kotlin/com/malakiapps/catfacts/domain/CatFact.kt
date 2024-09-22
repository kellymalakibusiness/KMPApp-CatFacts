package com.malakiapps.catfacts.domain

data class CatFact(
    val isHorizontalCard: Boolean,
    val image: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val text: String,
    val liked: Boolean,
    val disliked: Boolean,
    val downloaded: Boolean
)
