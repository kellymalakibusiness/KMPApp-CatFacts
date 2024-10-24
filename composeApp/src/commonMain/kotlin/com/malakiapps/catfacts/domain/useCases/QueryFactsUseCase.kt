package com.malakiapps.catfacts.domain.useCases

import com.malakiapps.catfacts.data.common.HttpException
import com.malakiapps.catfacts.data.common.getOrThrow
import com.malakiapps.catfacts.data.common.onError
import com.malakiapps.catfacts.data.fact.FactRepository
import com.malakiapps.catfacts.data.image.CatImageRepository
import com.malakiapps.catfacts.data.localDatabase.LocalStorageRepository
import com.malakiapps.catfacts.domain.CatFact
import com.malakiapps.catfacts.domain.CatFactPayload
import com.malakiapps.catfacts.domain.CatFactPayload.CatFactListResponse.Companion.toCatFactListResponse
import com.malakiapps.catfacts.domain.CatFactPayload.ErrorResponse.Companion.toErrorResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class QueryFactsUseCase(
    private val factRepository: FactRepository,
    private val imageRepository: CatImageRepository,
    private val localStorageRepository: LocalStorageRepository
) {

    suspend fun invoke(): CatFactPayload {
        val count = 10

        return try {
            coroutineScope {
                val factsDeferredResponse = async { factRepository.getFacts(count = count) }
                val imagesDeferredResponse = async { imageRepository.getImages(count = count) }

                val factsResponse = factsDeferredResponse.await()
                    .getOrThrow(
                        onDefineErrorMessage = { "Facts error: $it" }
                    )
                val imageResponse = imagesDeferredResponse.await()
                    .getOrThrow(
                        onDefineErrorMessage = { "Image error: $it" }
                    )

                factsResponse.mapIndexed { index, fact ->
                    val image = imageResponse[index]

                    localStorageRepository.getFactByText(text = fact)?.let { localFact ->
                        if(localFact.image == ""){
                            localFact.copy(
                                isHorizontalCard = image.height > image.width,
                                image = image.url,
                                imageWidth = image.width,
                                imageHeight = image.height,
                                text = fact
                            )
                        } else {
                            localFact
                        }
                    } ?: CatFact(
                        isHorizontalCard = image.height > image.width,
                        image = image.url,
                        imageWidth = image.width,
                        imageHeight = image.height,
                        text = fact,
                        liked = false,
                        downloaded = false,
                        disliked = false
                    )
                }.toCatFactListResponse()
            }
        } catch (e: HttpException){
            e.toErrorResponse()
        }
    }
}