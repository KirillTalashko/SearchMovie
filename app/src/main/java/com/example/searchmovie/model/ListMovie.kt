package com.example.searchmovie.model

import com.google.gson.annotations.SerializedName

data class ListMovie(
    @SerializedName("lists") val list: MutableList<String>
)