package com.example.searchmovie.presentation.modelMovie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUi(
    val id: Long,
    val name: String?,
    val poster: PosterUi?,
    val rating: RatingUi,
    val duration: Int,
    val year: Int,
    val genres: List<GenresUi>?,
    val type: Int,
    val description: String?,
    val date: Long?
) : Parcelable



