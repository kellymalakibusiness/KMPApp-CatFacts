package com.malakiapps.catfacts.data.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDatastore(dataStorePath: DataStorePath): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { dataStorePath.path.toPath() }
    )
}

expect class GetDataStorePath{
    fun getThePath(): DataStorePath
}

data class DataStorePath(
    val path: String
)

internal const val DATASTORE_FILE_NAME = "catfacts.preferences_pb"

data object DataStoreKeys {
    val language = "language"
}