package com.projects.moviesapp.android.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviesapp.domain.model.Movie
import com.projects.moviesapp.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeScreenState())
    private var currentPage = 1

    init {
        Log.d(TAG, "++++++++++++++++++++++ view model")
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
                Log.d(TAG, "8888888888")

               val resultMovies = getMoviesUseCase(page = currentPage)
                Log.d(TAG, "77777777")
                Log.d(TAG, "${resultMovies}")
                val movies = if (currentPage == 1) resultMovies else uiState.movies + resultMovies
                Log.d(TAG, "6666666")
                currentPage += 1
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = resultMovies.isEmpty(),
                    movies = movies
                )

            } catch (error: Throwable) {
                Log.d(TAG, "Could not load movies  Sodiqjon: ${error.localizedMessage}")
                Log.d(TAG, "Could not load movies  Sodiqjon: ${error.message}")
                uiState = uiState.copy(
                    loading = false,
                    refreshing = false,
                    loadFinished = true,
                    errorMessage = "Could not load movies: ${error.localizedMessage}"
                )
            }
        }
    }
}


data class HomeScreenState(
    var loading: Boolean = false,
    var refreshing: Boolean = false,
    var movies: List<Movie> = listOf(),
    var errorMessage: String? = null,
    var loadFinished: Boolean = false
)