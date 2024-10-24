package com.malakiapps.catfacts.domain

import com.malakiapps.catfacts.data.common.HttpException

sealed interface CatFactPayload {
    data class ErrorResponse(
        val message: String
    ): CatFactPayload {
        companion object {
            fun HttpException.toErrorResponse(): ErrorResponse {
                return ErrorResponse(message = message)
            }
        }
    }

    data class CatFactListResponse(
        val data: List<CatFact>
    ): CatFactPayload {
        companion object {
            fun List<CatFact>.toCatFactListResponse(): CatFactListResponse {
                return CatFactListResponse(
                    data = this
                )
            }
        }
    }
}