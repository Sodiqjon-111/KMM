package com.projects.moviesapp.data.repository

import com.projects.moviesapp.data.remote.RemoteDataSource
import com.projects.moviesapp.data.util.toMovie
import com.projects.moviesapp.domain.model.MainMovie
import com.projects.moviesapp.domain.repository.MovieRepository

internal class MovieRepositoryImpl(
    private val remoteDateSource: RemoteDataSource
): MovieRepository {

    override suspend fun getMovies(page: Int): List<MainMovie> {
        return remoteDateSource.getMovies(page = page).results.map {
            it.toMovie()
        }
    }

    override suspend fun getMovie(movieId: Int): MainMovie {
        return remoteDateSource.getMovie(movieId = movieId).toMovie()
    }
}