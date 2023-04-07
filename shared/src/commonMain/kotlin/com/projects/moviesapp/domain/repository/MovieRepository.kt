package com.projects.moviesapp.domain.repository

import com.projects.moviesapp.domain.model.MainMovie

interface MovieRepository {
    suspend fun getMovies(page: Int): List<MainMovie>

    suspend fun getMovie(movieId: Int): MainMovie
}