package com.example.searchmovie.presentation.utils.extension

import com.example.logic.model.GenreLogic
import com.example.searchmovie.presentation.modelMovie.GenresUi

fun List<GenreLogic>?.toGenreUi(): List<GenresUi> {
    val genres = mutableListOf<GenresUi>()
    this?.forEach { genre ->
        genres.add(GenresUi(genre.name))
    }
    return genres
}
