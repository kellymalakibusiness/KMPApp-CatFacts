package com.malakiapps.catfacts.domain.useCases

import com.malakiapps.catfacts.data.common.getOrNull
import com.malakiapps.catfacts.data.fact.FactRepository
import com.malakiapps.catfacts.data.image.CatImageRepository
import com.malakiapps.catfacts.domain.CatFact
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class QueryFactsUseCase(
    private val factRepository: FactRepository,
    private val imageRepository: CatImageRepository
) {

    suspend fun invoke(): List<CatFact> {
        val count = 10

        return coroutineScope {
            val factsDeferredResponse = async { factRepository.getFacts(count = count) }
            val imagesDeferredResponse = async { imageRepository.getImages(count = count) }

            val factsResponse = factsDeferredResponse.await().getOrNull() ?: emptyList()//TODO("Return some type of error on null")
            val imageResponse = imagesDeferredResponse.await().getOrNull() ?: emptyList()//TODO("Return some type of error on null")

            factsResponse.mapIndexed { index, fact ->
                val image = imageResponse[index]
                CatFact(
                    isHorizontalCard = image.height > image.width,
                    image = image.url,
                    imageWidth = image.width,
                    imageHeight = image.height,
                    text = fact,
                    liked = false, //Implement something to check this
                    downloaded = false, //Implement something to check this
                    disliked = false //Implement something to check this
                )
            }
        }
    }
}