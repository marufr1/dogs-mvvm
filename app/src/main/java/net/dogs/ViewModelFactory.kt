package net.dogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.dogs.data.DogRepository

class ViewModelFactory(private val repository: DogRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return super.create(modelClass)
        } catch (e: Exception) {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}