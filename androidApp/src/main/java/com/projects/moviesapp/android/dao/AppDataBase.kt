package com.projects.moviesapp.android.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Movies::class], version = 5, exportSchema = false)
@TypeConverters(MoviesTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
