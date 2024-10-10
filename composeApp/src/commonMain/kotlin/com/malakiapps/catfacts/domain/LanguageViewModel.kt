package com.malakiapps.catfacts.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakiapps.catfacts.data.common.DataStoreKeys
import com.malakiapps.catfacts.domain.SupportedLanguages.Companion.isoCodeToSupportedLanguage
import com.malakiapps.catfacts.domain.useCases.ReadCurrentLanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val readCurrentLanguageUseCase: ReadCurrentLanguageUseCase,
    private val catFactsDataStorePreferences: DataStore<Preferences>
): ViewModel() {
    private val _currentLanguage = MutableStateFlow<SupportedLanguages?>(null)
    val currentLanguage = _currentLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            _currentLanguage.value = readCurrentLanguageUseCase.invoke()
        }
    }

    fun updateLanguage(selectedLanguage: SupportedLanguages){
        viewModelScope.launch {
            catFactsDataStorePreferences.edit {
                val languageKey = stringPreferencesKey(DataStoreKeys.language)
                it[languageKey] = selectedLanguage.isoCode
            }
            _currentLanguage.value = selectedLanguage
        }
    }
}