package com.apiguave.newTins.ui.main

import android.app.Application
import com.apiguave.newTins.ui.di.appModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MainApp)
            modules(appModule)
        }
    }
}