package com.example.searchmovie.core.model

import android.os.Parcelable
import com.example.network.modelsMovie.Genres
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUi(
    val id: Long,
    val name: String?,
    val poster: Poster?,
    val rating: Rating,
    val duration: Int,
    val year: Int,
    val genres: List<Genres>?,
    val type: Int,
    val description: String?,
    val date: Long?
) : Parcelable



