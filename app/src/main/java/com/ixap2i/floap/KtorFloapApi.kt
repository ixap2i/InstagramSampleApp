package com.ixap2i.floap

import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import org.koin.ktor.ext.Koin.Feature.install


class KtorFloapApi : FloapApi {
    override val OkHttp: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    private val apiEndpoint = "https://api.instagram.com/oauth/authorize/"

    private val httpClient = HttpClient() {
//        defaultRequest {
//            headers.appendAll(baseHeaders)
//        }
//        install(JsonFeature) {
//            serializer = KotlinxSerializer(Json.nonstrict)
//        }
    }

    private val extraHttpClient by lazy { HttpClient(OkHttp) }

    private var baseHeaders: Headers = headersOf()

    override fun resetHeader() {
        baseHeaders = headersOf()
    }

    private fun setBaseHeaders(headers: Headers) {
        val accessToken = headers[ACCESS_TOKEN_KEY]
        val client = headers[CLIENT_KEY]
        val expiry = headers[EXPIRY_KEY]
        val uid = headers[UID_KEY]

        if (accessToken == null || client == null || expiry == null || uid == null) {
            throw IllegalArgumentException("invalid headers")
        }

        baseHeaders = headersOf(
            ACCESS_TOKEN_KEY to listOf(accessToken),
            CLIENT_KEY to listOf(client),
            EXPIRY_KEY to listOf(expiry),
            UID_KEY to listOf(uid)
        )
    }

    // TODO  createRequestResult

    override suspend fun getUserImage(): Result<ImageResponse, ImageErrorResponse> {
        return createRequestResult<ImageResponse, ImageErrorResponse>(
            responseDeserializationStrategy = ImageRepository.serializer(),
            errorResponseFactory = ImageErrorResponse()
        ) {
            method = io.ktor.http.HttpMethod.Post
            contentType(io.ktor.http.ContentType.Application.Json)
            url("$apiEndpoint/sms_authentications")
            body = PhoneNumberParameter(phoneNumber)
        }.getResult()

        override suspend fun getAccessKey(phoneNumber: String): Result<ImageResponse, ImageErrorResponse> {
            return createRequestResult<ImageResponse, ImageRepository, ImageErrorResponse>(
                responseDeserializationStrategy = ImageRepository.serializer(),
                errorResponseFactory = ImageErrorResponse
            ) {
                method = io.ktor.http.HttpMethod.Post
                contentType(io.ktor.http.ContentType.Application.Json)
                url("$apiEndpoint/sms_authentications")
                body = PhoneNumberParameter(phoneNumber)
            }.getResult()
        }

        override suspend fun takeOver(): Result<ImageResponse, Unit> {
            return createRequestResult<ImageResponse, ImageRepository, Unit>(
                responseDeserializationStrategy = ImageRepository.serializer()
            ) {
                method = io.ktor.http.HttpMethod.Post
                url("$apiEndpoint/users/me/take_over")
                body = ByteArrayContent(kotlin.byteArrayOf())
            }.getResult()
        }
    }
    companion object {
        private const val ACCESS_TOKEN_KEY = "1298128612.185bc9b.ff153ae1abc944388394c4b8acef27d8"
        private const val CLIENT_KEY = "185bc9b4b55a4afe9922aed4db639b2b"
        private const val EXPIRY_KEY = "expiry"
        private const val UID_KEY = "1298128612"
    }
}


