package com.desinta.dogsmvvmtrial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desinta.dogsmvvmtrial.data.DogRepository
import com.desinta.dogsmvvmtrial.data.local.Dog
import com.desinta.dogsmvvmtrial.data.model.SearchResponse

class PicturesViewModel(private val repository: DogRepository) : ViewModel() {

    var view: PicturesView? = null

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> = _dogs

    suspend fun fetchAndLoadDog() {
        view?.showLoading()
        // fetch data from API
        val result = repository.getDogs()
        result?.let {
            // if data not null, save or replace to table dogs
            repository.saveDogs(result)
        }

        // get latest data dogs from local db
        val loadFromDatabase = repository.loadDogs()
        _dogs.postValue(loadFromDatabase)
        view?.hideLoading()
    }
}