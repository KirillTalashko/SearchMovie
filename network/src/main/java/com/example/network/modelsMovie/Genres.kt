package com.example.network.modelsMovie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genres(
    @SerializedName("name") val genresName: String?
) : Parcelable
