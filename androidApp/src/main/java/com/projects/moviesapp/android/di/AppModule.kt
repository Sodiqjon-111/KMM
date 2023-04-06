package com.projects.moviesapp.android.di

import com.projects.moviesapp.android.detail.DetailViewModel
import com.projects.moviesapp.android.favourite.FavouriteViewModel
import com.projects.moviesapp.android.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get()) }
   // viewModel { MoviesViewModel(get()) }
    viewModel { FavouriteViewModel() }
    viewModel { params -> DetailViewModel(getMovieUseCase = get(), movieId = params.get()) }
}