package com.projects.moviesapp.android.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Movies::class], version = 4, exportSchema = false)
@TypeConverters(MoviesTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDataBase? = null
//
//        fun getDatabase(context: Context): AppDataBase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDataBase::class.java,
//                    "movies_database"
//                ).build()
//
//                INSTANCE = instance
//                instance
//            }
//        }
//    }

}
