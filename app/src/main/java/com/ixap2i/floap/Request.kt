package com.ixap2i.floap

import com.typesafe.config.ConfigException

sealed class Result<T, R> {
    class Success<T, R>(val value: T): Result<T, R>()

    class Failure<T, R>(val reason: FailureReason<R>) : Result<T, R>()

    fun isSuccess() = this is Success

    val failureReason: FailureReason<R>?
        get() = (this as? Failure)?.reason

    val expectedFailureReason: R?
        get() {
            val expected = failureReason as? FailureReason.Expected ?: return null
            return expected.reason
        }
}

sealed class FailureReason<R> {
    class Expected<R>(val reason: R) : FailureReason<R>()
    class Unexpected<R> : FailureReason<R>()
}
