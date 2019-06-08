package com.ixap2i.floap

import org.koin.core.context.startKoin

val module = org.koin.dsl.module {
    single<ImageService> { ImageServiceImpl(get()) }
    // get() Will resolve HelloRepository
    single { ImageRepository() }}

fun main(vararg args: String) {
    startKoin {
        modules(module)
    }
}
