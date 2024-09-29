package com.malakiapps.catfacts.data.localDatabase

import androidx.compose.runtime.mutableStateOf
import com.malakiapps.catfacts.domain.CatFact
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class SavedCatFactDTO: RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    @Index
    var text: String = ""
    var isHorizontalCard: Boolean = true
    var image: String = ""
    var imageWidth: Int? = null
    var imageHeight: Int? = null
    var liked: Boolean = false
    var disliked: Boolean = false
    var downloaded: Boolean = false

    fun toCatFact(): CatFact = CatFact(
        id = _id,
        text = text,
        isHorizontalCard = isHorizontalCard,
        image = image,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        liked = liked,
        disliked = disliked,
        downloaded = downloaded
    )

    companion object {
        fun CatFact.fromCatFact(): SavedCatFactDTO {
            return SavedCatFactDTO().apply {
                text = this@fromCatFact.text
                image = this@fromCatFact.image
                imageWidth = this@fromCatFact.imageWidth
                imageHeight = this@fromCatFact.imageHeight
                liked = this@fromCatFact.liked
                disliked = this@fromCatFact.disliked
                downloaded = this@fromCatFact.downloaded
            }
        }
    }
}