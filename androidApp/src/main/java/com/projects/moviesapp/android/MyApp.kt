package com.projects.moviesapp.android

import android.app.Application
import com.projects.moviesapp.android.di.appModule
import com.projects.moviesapp.di.getSharedModules
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + getSharedModules())
        }
    }
}