package com.malakiapps.catfacts.domain

import kotlinx.coroutines.flow.MutableStateFlow

expect class ImageSelector {
    val image: MutableStateFlow<ByteArray?>
    suspend fun launchImagePicker()
}