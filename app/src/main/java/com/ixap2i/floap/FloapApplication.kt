package com.ixap2i.floap

import android.app.Application
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


class FloapApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FloapApplication)
            modules(appModule)
        }

        val imageStore: ImageRepository = get<ImageRepository>()
    }
}

val appModule = module {
    single<FloapApplication> { FloapApplication() }
    single { ImageServiceImpl(androidContext(), floapApi = get()) }
}
//fun Application.main() {
//    install(DefaultHeaders)
//    install(CallLogging)
//    install(ContentNegotiation) {
//        gson {
//            setPrettyPrinting()
//        }
//    }
//}
//fun Application.routes() {
//    val service: ImageService by inject()
//
//   routing {
//       route("https://api.instagram.com/oauth/authorize/?client_id=185bc9b4b55a4afe9922aed4db639b2b&redirect_uri=https://www.yahoo.co.jp/&response_type=code") {
//           get  {
//               call.respondText(service.getCode())
//           }
//        }
//    }
//}
