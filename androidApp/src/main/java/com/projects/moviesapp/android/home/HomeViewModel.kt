package com.projects.moviesapp.android.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviesapp.domain.model.Movie
import com.projects.moviesapp.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    val _myListLiveData = MutableLiveData<MutableList<Movie>>()
    val myListLiveData: LiveData<MutableList<Movie>> get() = _myListLiveData

    var uiState by mutableStateOf(HomeScreenState())
    var viewModelAllList = emptyList<Movie>()
    var  favouriteList = MutableLiveData<MutableList<Movie>>()
    private var currentPage = 1

    init {
        loadMovies(forceReload = false)
    }


    fun loadMovies(forceReload: Boolean = false) {
        if (uiState.loading) return
        if (forceReload) currentPage = 1
        if (currentPage == 1) uiState = uiState.copy(refreshing = true)

        viewModelScope.launch {
            uiState = uiState.copy(
                loading = true
            )

            try {
                val resultMovies = getMoviesUseCase(page = currentPage)
                Log.d(TAG, "${resultMovies}")
                val movies = if (currentPage == 1) resultMovies else uiState.movies + resultMovies
                currentPage += 1
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = resultMovies.isEmpty(),
                    movies = movies
                )
                viewModelAllList=uiState.movies


            } catch (error: Throwable) {
                Log.d(TAG, "Could not load movies  Sodiqjon: ${error.localizedMessage}")
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = true,
                    errorMessage = "Could not load movies: ${error.localizedMessage}"
                )
            }
        }
    }
    fun addToList(item: Movie) {
        val currentlist = _myListLiveData.value ?: mutableListOf()
        currentlist.add(item)
        _myListLiveData.value = currentlist
    }


    fun removeFromList(item: Movie) {
        val currentlist = _myListLiveData.value ?: mutableListOf()
        currentlist.add(item)
        _myListLiveData.value = currentlist
    }

    fun getList(): MutableList<Movie> {
        return _myListLiveData.value ?: mutableListOf()
    }


}


data class HomeScreenState(
    var loading: Boolean = false,
    var refreshing: Boolean = false,
    var movies: List<Movie> = listOf(),
    var errorMessage: String? = null,
    var loadFinished: Boolean = false
)