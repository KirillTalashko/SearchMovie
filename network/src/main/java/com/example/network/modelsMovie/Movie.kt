package com.example.network.modelsMovie


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val poster: Poster?,
    @SerializedName("rating") val rating: Rating,
    @SerializedName("duration") val movieLength: Short,
    @SerializedName("year") val year: Short,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("type") val typeNumber: Byte,
    @SerializedName("description") val description: String?
)


