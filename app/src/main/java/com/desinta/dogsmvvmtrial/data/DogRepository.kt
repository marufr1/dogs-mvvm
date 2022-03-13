package com.desinta.dogsmvvmtrial.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.desinta.dogsmvvmtrial.data.local.Dog
import com.desinta.dogsmvvmtrial.data.local.DogsDatabase
import com.desinta.dogsmvvmtrial.data.model.SearchResponse
import com.desinta.dogsmvvmtrial.data.model.SearchResponseItem
import com.desinta.dogsmvvmtrial.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository(context: Context) {

    private val retrofit = ApiClient.instance
    private val database = DogsDatabase.getInstance(context)

    suspend fun getDogs(): SearchResponse? {

        var response: SearchResponse?

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            response = try {
                retrofit.searchImages(size = "small", hasBreeds = true, limit = 20).body()
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