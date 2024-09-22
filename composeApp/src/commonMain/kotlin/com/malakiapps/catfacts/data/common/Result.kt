package com.malakiapps.catfacts.data.common

sealed interface Result<out D, out E: Error> {
    data class ResultSuccess<out D>(val data: D): Result<D, Nothing>
    data class ResultError<out E: Error>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.ResultError -> Result.ResultError(error)
        is Result.ResultSuccess -> Result.ResultSuccess(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

fun <T>Result<T, Error>.getOrNull(): T?{
    return when(this){
        is Result.ResultError -> null
        is Result.ResultSuccess -> data
    }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.ResultError -> this
        is Result.ResultSuccess -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.ResultError -> {
            action(error)
            this
        }
        is Result.ResultSuccess -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>