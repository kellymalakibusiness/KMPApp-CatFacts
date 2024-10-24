package com.malakiapps.catfacts.data.common

enum class NetworkError(val message: String) : Error {
    REQUEST_TIMEOUT("The request took too long to respond"),
    UNAUTHORIZED("The api call is not authorized"),
    CONFLICT("A conflict occurred while making the api call"),
    TOO_MANY_REQUESTS("Too many requests error"),
    NO_INTERNET("An error with the internet connection occurred"),
    PAYLOAD_TOO_LARGE("Payload too large"),
    SERVER_ERROR("An internal server error occurred"),
    SERIALIZATION("Found an issue with serializing the data"),
    UNKNOWN("An unhandled error was received. Unknown");
}