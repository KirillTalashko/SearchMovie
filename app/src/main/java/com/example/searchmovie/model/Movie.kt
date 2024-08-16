package com.example.searchmovie.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("message") val message: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: Poster
)

