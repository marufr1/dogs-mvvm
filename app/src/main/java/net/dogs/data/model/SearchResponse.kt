package net.dogs.data.model

import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("data")
    val searchResponseItem: ArrayList<SearchResponseItem>
)