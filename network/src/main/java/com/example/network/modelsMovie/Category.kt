package com.example.network.modelsMovie

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name") val name: String?,
    @SerializedName("slug") val slug: String?
)