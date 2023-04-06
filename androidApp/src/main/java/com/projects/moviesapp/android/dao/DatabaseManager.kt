package com.projects.moviesapp.android.dao

import android.content.Context
import androidx.room.Room

object DatabaseManager {
    private var instance: AppDataBase? = null
    fun getInstance(context: Context): AppDataBase {
        if (instance == null) {
            synchronized(AppDataBase::class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, "my_database"
                    ).build()
                }
            }
        }
        return instance!!
    }
}