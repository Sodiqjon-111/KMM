package com.projects.moviesapp.android.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.moviesapp.domain.model.MainMovie

class FavouriteViewModel(
) : ViewModel() {
    val _myListLiveData = MutableLiveData<MutableList<MainMovie>>()
    val myListLiveData: LiveData<MutableList<MainMovie>> get() = _myListLiveData


    init {

    }

    fun addToList(item: MainMovie) {
        val currentlist = _myListLiveData.value ?: mutableListOf()
        currentlist.add(item)
        _myListLiveData.value = currentlist
    }


    fun removeFromList(item: MainMovie) {
        val currentlist = _myListLiveData.value ?: mutableListOf()
        currentlist.add(item)
        _myListLiveData.value = currentlist
    }

    fun getList(): MutableList<MainMovie> {
        return _myListLiveData.value ?: mutableListOf()
    }

}
