package com.ixap2i.floap

import io.ktor.util.Hash
import okhttp3.Response

interface RequestParameter {
    fun toFilterParams(): List<Pair<String, String>>
}

val FILTERED_VAL get() = "[FILTERED]"


interface ImageResponse {
    val attribution: String?
    val caption: List<Caption>
    val comments: HashMap<String, Int>
    val crated_time: String
    val filter :String
    val id :String
    val images :Images
    val likes: HashMap<String, Int>
    val link :String
    val location :String?
    val tags: Array<String>
    val type: String
    val user: User
    val user_has_liked: Boolean
    val users_in_photo: Array<String>?
}

enum class ImageErrorResponse {
    USER_ID_INVALID, PHONE_NUMBER_REGISTERED
}

class BadResponseStatusException(
    val statusCode: String,
    val response: Response
) : IllegalStateException("Received bad status code: $statusCode. Expected status code < 300.")
