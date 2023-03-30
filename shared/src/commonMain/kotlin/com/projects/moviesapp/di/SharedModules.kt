package com.projects.moviesapp.di

import com.projects.moviesapp.data.remote.MovieService
import com.projects.moviesapp.data.remote.RemoteDataSource
import com.projects.moviesapp.data.repository.MovieRepositoryImpl
import com.projects.moviesapp.domain.repository.MovieRepository
import com.projects.moviesapp.domain.usecase.GetMovieUseCase
import com.projects.moviesapp.domain.usecase.GetMoviesUseCase
import com.projects.moviesapp.util.provideDispatcher
import org.koin.dsl.module

private val dataModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { MovieService() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    factory { GetMoviesUseCase() }
    factory { GetMovieUseCase() }
}

private val sharedModules = listOf(domainModule, dataModule, utilityModule)

fun getSharedModules() = sharedModules
