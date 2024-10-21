package com.example.network.modelsMovie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @SerializedName("kp") val kp: Float,
    @SerializedName("imd") val imd: Float
) : Parcelable
