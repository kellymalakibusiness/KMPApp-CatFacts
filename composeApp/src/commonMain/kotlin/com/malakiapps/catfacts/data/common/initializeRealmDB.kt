package com.malakiapps.catfacts.data.common

import com.malakiapps.catfacts.data.localDatabase.ByteArrayImage
import com.malakiapps.catfacts.data.localDatabase.SavedCatFactDTO
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

fun initializeRealmDB(): Realm {
    return Realm.open(
        configuration = RealmConfiguration.create(
            schema = setOf(
                SavedCatFactDTO::class,
                ByteArrayImage::class
            )
        )
    )
}