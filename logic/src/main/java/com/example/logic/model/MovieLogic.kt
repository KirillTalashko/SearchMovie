package com.example.logic.model

import com.example.network.modelsMovie.Genres
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating

data class MovieLogic(
    val id: Long,
    val name: String?,
    val poster: Poster?,
    val rating: Rating,
    val duration: Int,
    val year: Int,
    val genres: List<Genres>?,
    val type: Int,
    val description: String?,
    val data: Long?
)
