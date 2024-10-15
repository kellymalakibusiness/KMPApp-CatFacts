package com.malakiapps.catfacts.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val imageSelector: ImageSelector
): ViewModel() {
    val userProfileImage: StateFlow<ByteArray?> = imageSelector.image

    init {
        //Initial image loading thingy
    }

    fun updateUserProfileImage(){
        viewModelScope.launch {
            imageSelector.launchImagePicker()
        }
    }
}