package com.ixap2i.floap

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)

    install(Koin) {
        modules(module)
    }

    val service: ImageService by inject()

    routing {
        get("/hello") {
            call.respondText(service.sayHello())
        }
    }
}