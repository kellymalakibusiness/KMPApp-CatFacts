package com.malakiapps.catfacts.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakiapps.catfacts.data.common.DataStoreKeys
import com.malakiapps.catfacts.data.localDatabase.LocalStorageRepository

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val imageSelector: ImageSelector,
    private val catFactsDataStorePreferences: DataStore<Preferences>,
    private val localStorageRepository: LocalStorageRepository
) : ViewModel() {
    val userProfileImage: StateFlow<ByteArray?> = imageSelector.image

    init {
        viewModelScope.launch {
            //Before we start reading, we check if we already have a saved image on database
            initialFetchCurrentImage()
        }
    }

    private suspend fun initialFetchCurrentImage() {
        val imageId = getImageId()
        if (imageId != null) {
            imageSelector.image.update {
                localStorageRepository.readImageByteArray(id = imageId)
            }

        }
    }

    fun updateUserProfileImage() {
        viewModelScope.launch {
            imageSelector.launchImagePicker()
        }
    }

    fun saveUserDetails(name: String){
        viewModelScope.launch {
            //Name
            catFactsDataStorePreferences.edit {
                val usernameKey = stringPreferencesKey(DataStoreKeys.username)
                it[usernameKey] = name
            }

            //Image
            userProfileImage.value?.let { image ->
                val imageId = getImageId()

                if (imageId == null) {
                    //No image on the database, create a new one
                    val newId =
                        localStorageRepository.saveImageByteArray(id = null, image = image)
                    saveImageId(newId)
                } else {
                    localStorageRepository.saveImageByteArray(id = imageId, image = image)
                }
            }
        }
    }

    suspend fun getCurrentUsername(): String {
        return catFactsDataStorePreferences.data
            .firstOrNull()
            ?.get(stringPreferencesKey(DataStoreKeys.username)) ?: ""
    }

    private suspend fun getImageId(): String? {
        return catFactsDataStorePreferences.data
            .firstOrNull()
            ?.get(stringPreferencesKey(DataStoreKeys.image))
    }

    private suspend fun saveImageId(imageId: String) {
        catFactsDataStorePreferences.edit {
            val imageKey = stringPreferencesKey(DataStoreKeys.image)
            it[imageKey] = imageId
        }
    }
}