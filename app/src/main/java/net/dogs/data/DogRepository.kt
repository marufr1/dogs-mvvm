package net.dogs.data

import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import net.dogs.data.local.Dog
import net.dogs.data.local.DogDao
import net.dogs.data.network.ApiService
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getDogs(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        val dogs = dogDao.getDog()

        if (dogs.isEmpty()) {
            val response = apiService.searchImages()
            response.suspendOnSuccess {
                val dogsConvert = data.searchResponseItem.map {
                    Dog(
                        null,
                        it.id,
                        it.url,
                        it.breeds.elementAt(0).name,
                        it.breeds.elementAt(0).bredFor
                    )
                }
                dogDao.insertDog(dogsConvert)
                emit(dogsConvert)
            }.onError {
                onError(this.message())
            }.onException {
                onError(message)
            }
        } else {
            emit(dogDao.getDog())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)
}