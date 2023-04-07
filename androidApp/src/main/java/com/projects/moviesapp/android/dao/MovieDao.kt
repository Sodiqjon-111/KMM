package com.projects.moviesapp.android.dao


import androidx.room.*
import org.jetbrains.annotations.NotNull

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movies)

    @Query("SELECT * FROM my_database")
    fun getAllMovies(): List<Movies>

    @Query("DELETE FROM my_database WHERE id = :id")
    suspend fun deleteMovie(movie: Movies)
}