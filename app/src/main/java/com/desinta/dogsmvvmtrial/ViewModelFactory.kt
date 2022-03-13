package com.desinta.dogsmvvmtrial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.desinta.dogsmvvmtrial.data.DogRepository

class ViewModelFactory(private val repository: DogRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.getDeclaredConstructor(
                DogRepository::class.java
            ).newInstance(repository) as T
        } catch (e: Exception) {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}