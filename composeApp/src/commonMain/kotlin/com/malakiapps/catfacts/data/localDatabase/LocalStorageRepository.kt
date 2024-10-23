package com.malakiapps.catfacts.data.localDatabase

import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.FactsSummary
import kotlinx.coroutines.flow.Flow

interface LocalStorageRepository {

    fun getAllLocalFacts(): Flow<Map<String, CatFact>>

    fun getFactByText(text: String): CatFact?

    suspend fun onLikeFact(text: String, enabled: Boolean)

    suspend fun onDisLikeFact(text: String, enabled: Boolean)

    suspend fun onDownloadFact(catFact: CatFact, enabled: Boolean)

    suspend fun onDeleteSavedFact(catFact: CatFact)

    suspend fun saveImageByteArray(id: String?, image: ByteArray): String

    suspend fun readImageByteArray(id: String): ByteArray?

    suspend fun getFactsSummary(): FactsSummary

    fun getSavedFacts(): Flow<List<CatFact>>
}