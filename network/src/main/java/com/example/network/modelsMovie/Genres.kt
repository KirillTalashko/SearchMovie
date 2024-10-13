package com.example.network.modelsMovie

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genre") val name: String?
)
