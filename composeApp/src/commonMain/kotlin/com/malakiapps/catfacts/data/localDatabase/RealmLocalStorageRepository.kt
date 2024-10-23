package com.malakiapps.catfacts.data.localDatabase

import com.malakiapps.catfacts.data.localDatabase.SavedCatFactDTO.Companion.fromCatFact
import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.FactsSummary
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.BsonObjectId

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
            ).first().find()

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
                item.downloaded = enabled
            } else {
                val newItem = catFact.fromCatFact().apply { downloaded = enabled }
                copyToRealm(newItem)
            }
        }
    }

    override suspend fun onDeleteSavedFact(catFact: CatFact) {
        realm.write {
            val item = query<SavedCatFactDTO>(
                "text == $0",
                catFact.text
            )
                .first().find()

            if(item != null){
                item.downloaded = false
            }
        }
    }

    override suspend fun saveImageByteArray(id: String?, image: ByteArray): String {
        val finalId = id?.let {
            BsonObjectId(it)
        } ?: BsonObjectId()

        val currentItem = readImageByteArray(id = finalId.toHexString())

        if (!currentItem.contentEquals(image)){
            val newItem = ByteArrayImage().apply {
                this._id = finalId
                this.data = image
            }

            realm.write {
                copyToRealm(newItem)
            }
        }

        return finalId.toHexString()
    }

    override suspend fun readImageByteArray(id: String): ByteArray? {
        val bsonObjectId = BsonObjectId(id)
        val response = realm.query<ByteArrayImage>("_id == $0", bsonObjectId).first().find()

        return response?.data
    }

    override suspend fun getFactsSummary(): FactsSummary {
        var liked = 0
        var disliked = 0
        var saved = 0

        val savedFacts = realm
            .query<SavedCatFactDTO>()
            .find()
            .map {
                //Get all the summary values while converting to CatFact
                if(it.liked) liked++
                if(it.disliked) disliked++
                if(it.downloaded) saved++

                it.toCatFact()
            }

        val onlySavedFacts = savedFacts.filter { it.downloaded }

        return FactsSummary(
            likedCount = liked,
            dislikedCount = disliked,
            savedCount = saved,
            savedFacts = onlySavedFacts
        )
    }

    override fun getSavedFacts(): Flow<List<CatFact>> {
        return realm
            .query<SavedCatFactDTO>()
            .asFlow()
            .map { factList ->
                factList.list.mapNotNull {
                    if(it.downloaded){
                        it.toCatFact()
                    } else {
                        null
                    }
                }
            }
    }
}