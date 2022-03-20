package net.dogs.data.network

import com.skydoves.sandwich.ApiResponse
import net.dogs.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search")
    suspend fun searchImages(
        @Query("size") size: String? = null,                // thumb, small, med, full
        @Query("mime_types") mimeType: String? = null,      // jpg, png, etc
        @Query("format") format: String? = null,            // json, src, etc
        @Query("has_breeds") hasBreeds: Boolean? = null,    // only return img with breed data
        @Query("order") order: String? = null,              // RANDOM, ASC, DESC
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
    ): ApiResponse<SearchResponse>

}