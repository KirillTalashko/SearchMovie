package com.example.searchmovie.presentation.modelMovie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PosterUi(
    val url: String?
) : Parcelable