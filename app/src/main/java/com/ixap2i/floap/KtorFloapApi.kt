package com.ixap2i.floap

import io.ktor.http.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json


class KtorFloapApi : FloapApi, ImageRepository {
    override val OkHttp: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    private val apiEndpoint = "https://api.instagram.com/v1/users/1298128612/media/recent?access_token="

    private val httpClient = OkHttpClient()

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

        // TODO modify headars use companion
        baseHeaders = headersOf(
            ACCESS_TOKEN_KEY to listOf(accessToken),
            CLIENT_KEY to listOf(client),
            EXPIRY_KEY to listOf(expiry),
            UID_KEY to listOf(uid)
        )
    }

    override suspend fun getUserImage() :Result<ImageResponse, ImageErrorResponse> {
        return createRequestResult<ImageResponse, ImageResponceImpl, ImageErrorResponse>(
            responseDeserializationStrategy = null,
            errorResponseFactory = ImageErrorResponseFactory
        ) {}.getResult()
    }


    // TODO  createRequestResult

    private suspend fun <RESPONSE, RESPONSE_IMPL : RESPONSE, ERROR_RESPONSE> createRequestResult(
        responseDeserializationStrategy: DeserializationStrategy<RESPONSE_IMPL>? = null,
        errorResponseFactory: ErrorResponseFactory<ERROR_RESPONSE>? = null,
        responseHeaderHandler: (okhttp3.Headers) -> Unit = {},
        userDefaultHttpClient: Boolean = true,
        builderBlock: OkHttpClient.() -> Unit
    ): RequestResult<RESPONSE, ERROR_RESPONSE> {
        var result: RequestResult<RESPONSE, ERROR_RESPONSE>
        var responseBody: String? = null
        try {
            lateinit var response: Response

            val request = Request.Builder()
                .url("$apiEndpoint$ACCESS_TOKEN_KEY")
                .build()

                response = httpClient.newCall(request).execute()
                responseBody = response.body()!!.string()


            if (!response.isSuccessful) throw BadResponseStatusException(response.message(), response)
            responseHeaderHandler(response.headers())
            if (responseDeserializationStrategy != null) {
                result = RequestResult.Success(Json.nonstrict.parse(responseDeserializationStrategy, responseBody))
            } else {
                // Need to set RESPONSE to Unit when there is no need to parsing JSON
                @Suppress("UNCHECKED_CAST")
                result = RequestResult.Success(Unit as RESPONSE)
            }
        } catch (e: Throwable) {
            result = RequestResult.Unexpected(e, responseBody)
            if (errorResponseFactory != null && e is BadResponseStatusException) {
                when (e.statusCode.toInt()) {
                    400, 401 -> {
                        try {
                            RequestResult.Failure<RESPONSE, ERROR_RESPONSE>(
                                errorResponseFactory.create(responseBody!!)
                            )
                        } catch (jsonError: Throwable) {
                            RequestResult.Unexpected<RESPONSE, ERROR_RESPONSE>(jsonError, responseBody)
                        }
                    }
                    else -> null
                }?.let {
                    result = it
                }
            }
        }
        return result
    }

    private sealed class RequestResult<T, R> {
        class Success<T, R>(val value: T) : RequestResult<T, R>()
        class Failure<T, R>(val reason: R) : RequestResult<T, R>()
        class Unexpected<T, R>(val e: Throwable, val responseBody: String?) : RequestResult<T, R>()

        private fun tryToReport(allowNotFound: Boolean = false) {
            if (this !is Unexpected) return
            val error = e as? BadResponseStatusException ?: return
            if (allowNotFound && error.statusCode.toInt() == 404) return

            // TODO: use e and responseBody to report a request error
        }

        fun getResult(): Result<T, R> {
            tryToReport()
            return when (this) {
                is Success -> Result.Success(this.value)
                is Failure -> Result.Failure(FailureReason.Expected(this.reason))
                is Unexpected -> Result.Failure(FailureReason.Unexpected())
            }
        }

        // Trade 404 as a Success with null
        fun getNullableResult(): Result<T?, R> {
            tryToReport(true)
            return when (this) {
                is Success -> Result.Success(this.value)
                is Failure -> Result.Failure(FailureReason.Expected(this.reason))
                is Unexpected -> {
                    val error = this.e
                    if (error is BadResponseStatusException && error.statusCode.toInt() == 404) {
                        Result.Success<T?, R>(null)
                    } else {
                        Result.Failure(FailureReason.Unexpected())
                    }
                }
            }
        }
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "1298128612.185bc9b.ff153ae1abc944388394c4b8acef27d8"
        private const val CLIENT_KEY = "185bc9b4b55a4afe9922aed4db639b2b"
        private const val EXPIRY_KEY = "expiry"
        private const val UID_KEY = "1298128612"
    }
}


