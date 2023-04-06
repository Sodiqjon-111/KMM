package com.projects.moviesapp.android

import android.app.Application
import com.projects.moviesapp.android.dao.AppDataBase
import com.projects.moviesapp.android.dao.MovieRepository
import com.projects.moviesapp.android.di.appModule
import com.projects.moviesapp.di.getSharedModules
import org.koin.core.context.startKoin

class MyApp : Application() {
//
//    val database by lazy { AppDataBase.getDatabase(this) }
//    val repository by lazy { MovieRepository(database.movieDao()) }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + getSharedModules())
        }
    }
}