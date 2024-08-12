package com.example.searchmovie.model

import com.google.gson.annotations.SerializedName

data class PosterMovie(
    @SerializedName("name") val name: String,
    @SerializedName("poster") val poster: Poster
)

data class Poster(
    @SerializedName("url") val url: String?
)

