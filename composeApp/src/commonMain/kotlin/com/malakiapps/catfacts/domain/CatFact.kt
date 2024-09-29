package com.malakiapps.catfacts.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import org.mongodb.kbson.ObjectId

data class CatFact(
    val id: ObjectId? = null,
    val isHorizontalCard: Boolean,
    val image: String,
    val imageWidth: Int?,
    val imageHeight: Int?,
    val text: String,
    val liked: Boolean,
    val disliked: Boolean,
    val downloaded: Boolean
)
