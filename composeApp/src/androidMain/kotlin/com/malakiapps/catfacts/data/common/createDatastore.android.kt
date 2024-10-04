package com.malakiapps.catfacts.data.common

import android.content.Context

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class GetDataStorePath(private val context: Context) {
    actual fun getThePath(): DataStorePath {
        return DataStorePath(context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath)
    }
}