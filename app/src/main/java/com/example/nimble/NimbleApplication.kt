package com.example.nimble

import android.app.Application
import com.example.nimble.koin.appModule
import com.example.nimble.koin.dataStoreModule
import com.example.nimble.koin.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NimbleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NimbleApplication)
            modules(appModule, networkModule, dataStoreModule)
        }
    }
}
