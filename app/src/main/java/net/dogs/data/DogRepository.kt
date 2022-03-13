package net.dogs.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dogs.data.local.Dog
import net.dogs.data.local.DogsDatabase
import net.dogs.data.model.SearchResponse
import net.dogs.data.network.ApiClient

class DogRepository(context: Context) {

    private val retrofit = ApiClient.instance
    private val database = DogsDatabase.getInstance(context)

    suspend fun getDogs(): SearchResponse? {

        var response: SearchResponse?

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            response = try {
                retrofit.searchImages(size = "small", hasBreeds = true, limit = 5).body()
            } catch (e: Exception) {
                null
            }
        }
        return response
    }

    suspend fun saveDogs(searchResponse: SearchResponse): Boolean {
        var successInsert: Boolean
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val dogs = searchResponse.map {
                Dog(
                    null,
                    it.id,
                    it.url,
                    "null",
                    "null"
                )
            }
            successInsert = try {
                database?.dog()?.insertDog(dogs)
                true
            } catch (e: Exception) {
                false
            }
        }
        return successInsert
    }

    suspend fun loadDogs(): List<Dog> {
        var result: List<Dog>
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            result = database!!.dog().getDog()
        }
        return result
    }

}