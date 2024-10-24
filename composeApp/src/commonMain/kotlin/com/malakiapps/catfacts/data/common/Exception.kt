package com.malakiapps.catfacts.data.common

data class HttpException(
    override val message: String
): Exception()