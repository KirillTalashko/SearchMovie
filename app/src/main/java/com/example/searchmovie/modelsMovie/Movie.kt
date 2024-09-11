package com.example.searchmovie.modelsMovie

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: Poster?,
    @SerializedName("rating") val rating: Rating
)

