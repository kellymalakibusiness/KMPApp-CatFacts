package com.malakiapps.catfacts.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakiapps.catfacts.data.common.DataStoreKeys
import com.malakiapps.catfacts.data.localDatabase.LocalStorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val localStorageRepository: LocalStorageRepository,
    private val catFactsDataStorePreferences: DataStore<Preferences>,
): ViewModel() {
    private val _image: MutableStateFlow<ByteArray?> = MutableStateFlow(null)
    val image: StateFlow<ByteArray?> = _image.asStateFlow()

    private val _name: MutableStateFlow<String> = MutableStateFlow("Username")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _factSummary: MutableStateFlow<FactsSummary?> = MutableStateFlow(null)
    val factsSummary: StateFlow<FactsSummary?> = _factSummary.asStateFlow()

    init {
        fetchInitialData()
    }

    private fun fetchInitialData(){
        viewModelScope.launch {
            _name.update {
                getCurrentUsername()
            }

            _image.update {
                getImage()
            }

            _factSummary.update {
                localStorageRepository.getFactsSummary()
            }
        }
    }

    private suspend fun getImage(): ByteArray? {
        val imageId = catFactsDataStorePreferences.data
            .firstOrNull()
            ?.get(stringPreferencesKey(DataStoreKeys.image))

        return imageId?.let {
            localStorageRepository.readImageByteArray(id = it)
        }
    }

    suspend fun getCurrentUsername(): String {
        return catFactsDataStorePreferences.data
            .firstOrNull()
            ?.get(stringPreferencesKey(DataStoreKeys.username)) ?: ""
    }
}