package com.projects.moviesapp.android.dao


import Movies
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MoviesViewModel:ViewModel() {

  //  private val myDao: MovieDao
    //private val myDataBase= Room.databaseBuilder(application,AppDataBase::class.java,"my_database").build()


     init {
          //myDao=myDataBase.movieDao()
        }

    fun insertMovies(movies: Movies){
        viewModelScope.launch {
           // myDao.insert(movies)
        }
    }

}