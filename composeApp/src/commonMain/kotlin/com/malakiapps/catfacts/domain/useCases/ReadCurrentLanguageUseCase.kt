package com.malakiapps.catfacts.domain.useCases

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.malakiapps.catfacts.data.common.DataStoreKeys
import com.malakiapps.catfacts.domain.SupportedLanguages
import com.malakiapps.catfacts.domain.SupportedLanguages.Companion.isoCodeToSupportedLanguage
import kotlinx.coroutines.flow.firstOrNull

class ReadCurrentLanguageUseCase(
    private val catFactsDataStorePreferences: DataStore<Preferences>
) {

    suspend fun invoke(): SupportedLanguages {
        return catFactsDataStorePreferences.data
            .firstOrNull()
            ?.get(stringPreferencesKey(DataStoreKeys.language))
            ?.isoCodeToSupportedLanguage()
            ?: SupportedLanguages.ENGLISH
    }
}