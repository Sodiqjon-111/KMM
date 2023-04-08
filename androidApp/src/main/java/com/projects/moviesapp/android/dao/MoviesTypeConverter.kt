package com.projects.moviesapp.android.dao

import androidx.room.TypeConverter
import com.google.gson.Gson

class MoviesTypeConverter {
    @TypeConverter
    fun fromMovies(movies: Movies): String {
        return Gson().toJson(movies)
    }

    @TypeConverter
    fun toMovies(json: String): Movies {
        return Gson().fromJson(json, Movies::class.java)
    }
}