package com.malakiapps.catfacts.data.image

import kotlinx.serialization.Serializable

@Serializable
data class CatImageDOT(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
