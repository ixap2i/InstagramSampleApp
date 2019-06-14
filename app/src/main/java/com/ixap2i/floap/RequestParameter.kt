package com.ixap2i.floap

import io.ktor.http.HttpStatusCode
import io.netty.handler.codec.http.HttpResponse

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
    PHONE_NUMBER_INVALID, PHONE_NUMBER_REGISTERED
}

class BadResponseStatusException(
    val statusCode: HttpStatusCode,
    val response: HttpResponse
) : IllegalStateException("Received bad status code: $statusCode. Expected status code < 300.")
