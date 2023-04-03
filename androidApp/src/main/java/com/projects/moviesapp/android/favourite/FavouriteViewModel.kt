package com.projects.moviesapp.android.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.moviesapp.domain.model.Movie

class FavouriteViewModel(
) : ViewModel() {
    val _myListLiveData = MutableLiveData<MutableList<Movie>>()
    val myListLiveData: LiveData<MutableList<Movie>> get() = _myListLiveData


    init {

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
