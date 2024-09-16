package com.malakiapps.catfacts.common.di

import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result
import io.ktor.http.HttpStatusCode

fun HttpStatusCode.handleError(): Result.ResultError<NetworkError> {
    return when(this.value){
        401 -> Result.ResultError(NetworkError.UNAUTHORIZED)
        409 -> Result.ResultError(NetworkError.CONFLICT)
        408 -> Result.ResultError(NetworkError.REQUEST_TIMEOUT)
        413 -> Result.ResultError(NetworkError.PAYLOAD_TOO_LARGE)
        in 500..599 -> Result.ResultError(NetworkError.SERVER_ERROR)
        else -> Result.ResultError(NetworkError.UNKNOWN)
    }
}