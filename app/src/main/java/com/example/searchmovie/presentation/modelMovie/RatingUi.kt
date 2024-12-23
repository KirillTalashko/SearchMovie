package com.example.searchmovie.presentation.modelMovie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingUi(
    val kp: Float,
    val imd: Float
) : Parcelable