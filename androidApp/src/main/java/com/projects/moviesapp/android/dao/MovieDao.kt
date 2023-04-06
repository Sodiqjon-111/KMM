package com.projects.moviesapp.android.dao


import androidx.room.*
import org.jetbrains.annotations.NotNull

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movies)

    @Query("SELECT * FROM my_database")
    fun getAllMovies(): List<Movies>

//    @Query("SELECT * FROM my_database")
//    @NotNull
//    suspend fun deleteMovie(movie: Movies)
}