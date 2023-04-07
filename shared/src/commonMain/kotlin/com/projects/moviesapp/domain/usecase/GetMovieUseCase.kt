package com.projects.moviesapp.domain.usecase

import com.projects.moviesapp.domain.model.MainMovie
import com.projects.moviesapp.domain.repository.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetMovieUseCase : KoinComponent {
    private val repository: MovieRepository by inject()

    @Throws(Exception::class)
    suspend operator fun invoke(movieId: Int): MainMovie {
        return repository.getMovie(movieId = movieId)
    }
}