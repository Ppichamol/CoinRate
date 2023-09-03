package com.example.cinemo

import com.example.cinemo.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application: android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(viewModelModules))
        }
    }
}