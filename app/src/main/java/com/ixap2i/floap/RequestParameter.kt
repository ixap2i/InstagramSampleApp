package com.ixap2i.floap

import okhttp3.Response

interface RequestParameter {
    fun toFilterParams(): List<Pair<String, String>>
}

val FILTERED_VAL get() = "[FILTERED]"


interface ImageResponse {
    val phoneNumber: String
    val accessKey: String
    val confirmed: Boolean
}

enum class ImageErrorResponse {
    USER_ID_INVALID, PHONE_NUMBER_REGISTERED
}

class BadResponseStatusException(
    val statusCode: String,
    val response: Response
) : IllegalStateException("Received bad status code: $statusCode. Expected status code < 300.")
