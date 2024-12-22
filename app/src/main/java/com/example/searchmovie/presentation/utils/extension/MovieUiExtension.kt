package com.example.searchmovie.presentation.utils.extension

import com.example.logic.extension.toListGenreLogic
import com.example.logic.model.GenreLogic
import com.example.logic.model.MovieLogic
import com.example.searchmovie.presentation.modelMovie.MovieUi

fun MovieLogic.toMovieUi(): MovieUi {
    return MovieUi(
        id = this.id,
        name = this.name,
        poster = this.poster?.toPosterUi(),
        rating = this.rating.toRatingUi(),
        duration = this.duration,
        year = this.year,
        genres = genres.toGenreUi(),
        type = this.type,
        description = this.description,
        date = this.data
    )
}

fun List<MovieLogic>.toListMovieUi(): List<MovieUi> {
    val movies = mutableListOf<MovieUi>()
    this.forEach {
        movies.add(it.toMovieUi())
    }
    return movies
}

fun MovieUi.toMovieLogic(): MovieLogic {
    return MovieLogic(
        id = this.id,
        name = this.name,
        poster = this.poster.toPosterLogic(),
        rating = this.rating.toRatingLogic(),
        duration = this.duration,
        year = this.year,
        genres = genres.toListGenreLogic { GenreLogic(it.name) },
        type = this.type,
        description = this.description,
        data = date
    )
}