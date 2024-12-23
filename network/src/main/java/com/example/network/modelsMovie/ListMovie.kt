package com.example.network.modelsMovie

import com.google.gson.annotations.SerializedName


data class ListMovie(
    @SerializedName("docs") val movie: List<Movie>?,
    @SerializedName("limit") val limit: Long,
    @SerializedName("page") val page: Long
)
