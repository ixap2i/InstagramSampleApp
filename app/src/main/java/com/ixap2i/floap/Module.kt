package com.ixap2i.floap

import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin

//
val module = org.koin.dsl.module {
    single<ImageService> { ImageServiceImpl(get()) }
    single { ImageRepository() }}

fun main(args: Array<String>) {
    startKoin {
        modules(module)
        embeddedServer(Netty, commandLineEnvironment(args)).start()
    }
}
