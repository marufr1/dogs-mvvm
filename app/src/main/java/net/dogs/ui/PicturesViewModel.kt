package net.dogs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.dogs.data.DogRepository
import net.dogs.data.local.Dog
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel @Inject constructor(
    private val repository: DogRepository,
    private val ioDispatcher: CoroutineDispatcher
    ) : ViewModel() {

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> = _dogs
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    suspend fun fetchAndLoadDog() {
        viewModelScope.launch(ioDispatcher) {
            repository.getDogs(
                onStart = { _loading.postValue(true) },
                onComplete = { _loading.postValue(false) },
                onError = { _message.postValue(it) }
            ).collect {
                val result = it
                _dogs.postValue(result)
            }
        }
    }
}
