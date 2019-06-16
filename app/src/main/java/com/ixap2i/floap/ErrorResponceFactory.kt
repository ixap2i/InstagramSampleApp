package com.ixap2i.floap

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.JsonElementTypeMismatchException


@Serializable
class BaseError(
    val code: String
)

interface ErrorResponseFactory<T> {
    fun create(jsonString: String): T
}

object PhoneNumberErrorResponseFactory : ErrorResponseFactory<ImageErrorResponse> {

    override fun create(jsonString: String): ImageErrorResponse {
        // TODO: parse to kotshi
        val loginError = Json(JsonConfiguration(strictMode = false))
        return ImageErrorResponse.USER_ID_INVALID
//        return when(loginError.code) {
//            "sms_authentication_blank_phone_number_error",
//            "sms_authentication_invalid_phone_number_error",
//            "sms_authentication_invalid_format_phone_number_error" -> ImageErrorResponse.PHONE_NUMBER_INVALID
//            else -> throw JsonElementTypeMismatchException("code", "unexpected error code")
//        }
    }
}