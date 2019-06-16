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
    single { FloapApplication() }
}
