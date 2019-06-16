package com.ixap2i.floap

interface ErrorResponseFactory<T> {
    fun create(jsonString: String): T
}