package com.desinta.dogsmvvmtrial.data.model


import com.google.gson.annotations.SerializedName
import com.desinta.dogsmvvmtrial.data.model.Breed

data class SearchResponseItem(
    @SerializedName("breeds")
    val breeds: List<Breed>,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)