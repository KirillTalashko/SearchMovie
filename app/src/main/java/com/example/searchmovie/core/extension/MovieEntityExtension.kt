package com.example.searchmovie.core.extension

import com.example.database.modelEntity.MovieEntity
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating
import com.example.searchmovie.core.model.MovieUi

fun MovieEntity.toMovieUi(): MovieUi {
    return MovieUi(
        id = this.idMovieKp,
        name = this.name,
        poster = Poster(this.url),
        rating = Rating(this.ratingKp, this.ratingIMDb),
        duration = this.duration,
        year = this.year,
        genres = this.genres.toListGenres(),
        type = this.type,
        description = this.description,
        date = date
    )
}

fun List<MovieEntity>.toListMovieUi(): List<MovieUi> {
    val newListMovieUi = mutableListOf<MovieUi>()
    this.forEach { movieEntity ->
        newListMovieUi.add(movieEntity.toMovieUi())
    }
    return newListMovieUi
}