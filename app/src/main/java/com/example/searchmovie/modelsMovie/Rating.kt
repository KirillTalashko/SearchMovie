package com.example.searchmovie.modelsMovie

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("kp") val kp: Float,
    @SerializedName("imd") val imd: Float
)
