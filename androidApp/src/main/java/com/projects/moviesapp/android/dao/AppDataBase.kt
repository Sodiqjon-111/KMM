package com.projects.moviesapp.android.dao

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [MovieDao::class], version = 1, exportSchema = false)
abstract class AppDataBase :RoomDatabase(){
    abstract fun movieDao() :MovieDao
}
