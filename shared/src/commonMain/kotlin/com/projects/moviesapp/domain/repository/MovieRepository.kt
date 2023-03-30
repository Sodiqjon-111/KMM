package com.projects.moviesapp.domain.repository

import com.projects.moviesapp.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(page: Int): List<Movie>

    suspend fun getMovie(movieId: Int): Movie
}