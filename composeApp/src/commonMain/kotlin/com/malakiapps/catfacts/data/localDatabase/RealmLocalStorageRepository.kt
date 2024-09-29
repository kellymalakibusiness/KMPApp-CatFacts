package com.malakiapps.catfacts.data.localDatabase

import com.malakiapps.catfacts.data.localDatabase.SavedCatFactDTO.Companion.fromCatFact
import com.malakiapps.catfacts.domain.CatFact
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RealmLocalStorageRepository(
    private val realm: Realm
): LocalStorageRepository {

    override fun getAllLocalFacts(): Flow<Map<String, CatFact>> {
        return realm
            .query<SavedCatFactDTO>()
            .asFlow()
            .map { factList ->
                factList.list.associate {
                    it.text to it.toCatFact()
                }
            }
    }

    override fun getFactByText(text: String): CatFact? {
        return realm
            .query<SavedCatFactDTO>(
                "text == $0",
                text
            )
            .first().find()?.toCatFact()
    }

    override suspend fun onLikeFact(text: String, enabled: Boolean) {
        realm.write {
            val item = query<SavedCatFactDTO>(
                "text == $0",
                text
            )
                .first().find()

            item?.liked = enabled

            if(item == null){
                val newItem = SavedCatFactDTO().apply {
                    this.text = text
                    this.liked = enabled
                }
                copyToRealm(newItem)
            }
        }
    }

    override suspend fun onDisLikeFact(text: String, enabled: Boolean) {

        realm.write {
            val item = query<SavedCatFactDTO>(
                "text == $0",
                text
            ).first().find()

            item?.disliked = enabled
            if(item == null){
                val newItem = SavedCatFactDTO().apply {
                    this.text = text
                    this.disliked = enabled
                }
                copyToRealm(newItem)
            }
        }
    }

    override suspend fun onDownloadFact(catFact: CatFact, enabled: Boolean) {
        realm.write {
            val item = query<SavedCatFactDTO>(
                    "text == $0",
                    catFact.text
                )
                .first().find()

            if(item != null){
                item.image = catFact.image
                item.isHorizontalCard = catFact.isHorizontalCard
                item.imageWidth = catFact.imageWidth
                item.imageHeight = catFact.imageHeight
            } else {
                val newItem = catFact.fromCatFact().apply { downloaded = enabled }
                copyToRealm(newItem)
            }
        }
    }
}