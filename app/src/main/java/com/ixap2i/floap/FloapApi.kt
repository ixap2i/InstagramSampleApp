package com.ixap2i.floap

interface FloapApi {

    abstract val OkHttp: Any

    fun resetHeader()

//    suspend fun getUserImage(): Result<ImageResponse, ImageErrorResponse>
}