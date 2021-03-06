package com.ixap2i.floap

import androidx.lifecycle.LiveData
import okhttp3.Response

interface ImageResponse {
    val pagination: Pagination?
    val data: LiveData<List<ThumbnailImage>>

    fun getData() {
        this.data
    }
}

enum class ImageErrorResponse {
    USER_ID_INVALID, PHONE_NUMBER_REGISTERED
}

class BadResponseStatusException(
    val statusCode: String,
    val response: Response
) : IllegalStateException("Received bad status code: $statusCode. Expected status code < 300.")
