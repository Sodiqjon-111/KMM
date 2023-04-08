package com.projects.moviesapp.android.dao


import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movies)

    @Query("SELECT * FROM my_database")
    suspend fun getAllMovies(): List<Movies>

    @Delete
    suspend fun deleteMovie(movie: Movies)
}