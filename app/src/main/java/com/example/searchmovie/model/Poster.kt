package com.example.searchmovie.model

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("url") val url: String?
)