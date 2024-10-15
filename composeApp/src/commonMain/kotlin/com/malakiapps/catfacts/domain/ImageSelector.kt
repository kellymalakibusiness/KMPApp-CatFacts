package com.malakiapps.catfacts.domain

import kotlinx.coroutines.flow.StateFlow

expect class ImageSelector {
    val image: StateFlow<ByteArray?>
    suspend fun launchImagePicker()
}