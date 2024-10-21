package com.malakiapps.catfacts.data.localDatabase

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class ByteArrayImage: RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var data: ByteArray? = null
}
