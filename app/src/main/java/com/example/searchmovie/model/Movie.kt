package com.example.searchmovie.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: Poster
)

