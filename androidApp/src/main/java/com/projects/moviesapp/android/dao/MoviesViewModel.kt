package com.projects.moviesapp.android.dao


import Movies
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application
) : ViewModel() {


    val allProducts: List<Movies>
    private val repository: MovieRepository

    init {

        val movDataBase=AppDataBase.getDatabase(application)
        val movieDao=movDataBase.movieDao()

        repository = MovieRepository(movieDao)

        allProducts = repository.allMovies
    }



    fun insert(movies: Movies)=viewModelScope.launch {
        repository.insert(movies)
    }
}

