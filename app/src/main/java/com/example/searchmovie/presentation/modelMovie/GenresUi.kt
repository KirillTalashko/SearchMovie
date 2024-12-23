package com.example.searchmovie.presentation.modelMovie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresUi(
    val name: String?
) : Parcelable