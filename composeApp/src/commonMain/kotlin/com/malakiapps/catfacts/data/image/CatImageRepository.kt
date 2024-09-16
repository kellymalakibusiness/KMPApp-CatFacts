package com.malakiapps.catfacts.data.image

import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result

interface CatImageRepository {
    suspend fun getImages(count: Int): Result<List<CatImageDOT>, NetworkError>
}

