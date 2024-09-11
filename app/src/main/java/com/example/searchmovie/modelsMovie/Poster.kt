package com.example.searchmovie.modelsMovie

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("url") val url: String?
)