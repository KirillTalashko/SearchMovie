package com.example.network.modelsMovie

import com.google.gson.annotations.SerializedName


data class Movie(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: Poster?,
    @SerializedName("rating") val rating: Rating,
    @SerializedName("movieLength") val duration: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("genres") val genres: List<Genres>?,
    @SerializedName("typeNumber") val type: Int,
    @SerializedName("description") val description: String?
)


