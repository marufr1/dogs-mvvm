package net.dogs.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.dogs.data.local.Dog
import net.dogs.data.local.DogsDatabase
import net.dogs.data.model.SearchResponse
import net.dogs.data.model.SearchResponseItem
import net.dogs.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository(context: Context) {

    private val retrofit = ApiClient.instance
    private val database = DogsDatabase.getInstance(context)

    suspend fun getDogs() {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            retrofit.searchImages(size = "small", hasBreeds = true, limit = 20)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        if (response.code() == 200) {
                            val dogs = response.body()!!
                            if (dogs.isEmpty()) {
                                println("Tidak ada data anjing di server")
                            } else {
                                ArrayList<SearchResponseItem>(dogs)
                            }
                        } else {
                            println("Masalah koneksi ke server ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        println(t.message.toString())
                    }
                })
        }
    }

    suspend fun saveDogs(searchResponse: SearchResponse): Boolean {
        var successInsert: Boolean
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val dogs = searchResponse.map {
                Dog(
                    null,
                    it.id,
                    it.url,
                    it.breeds.elementAt(3).toString(),
                    it.breeds.elementAt(4).toString()
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