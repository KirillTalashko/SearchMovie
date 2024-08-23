package com.example.searchmovie.model

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("docs") val movie: List<Movie>,
    @SerializedName("limit") val limit: Long,
    @SerializedName("page") val page: Long
)
