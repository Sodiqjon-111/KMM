package com.projects.moviesapp.android.dao


import Movies
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movies)

    @Query("SELECT * FROM movies_database")
    fun getAllMovies():List<Movies>

    @Query("SELECT * FROM movies_database")
   suspend fun deleteMovie(movie: Movies)
}