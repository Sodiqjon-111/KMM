package com.projects.moviesapp.android.dao

import Movies

class MovieRepository(private val dataBase: AppDataBase) {
    suspend fun insertMovies(movies: Movies)=
        dataBase.movieDao().insert(movies)
}