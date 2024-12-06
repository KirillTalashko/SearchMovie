package com.example.logic.extension

import com.example.database.modelEntity.MovieEntity
import com.example.logic.model.MovieLogic
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating

fun MovieEntity.toMovieLogic(): MovieLogic {
    return MovieLogic(
        id = this.idMovieKp,
        name = this.name,
        poster = Poster(this.url),
        rating = Rating(this.ratingKp, this.ratingIMDb),
        duration = this.duration,
        year = this.year,
        genres = this.genres.toListGenres(),
        type = this.type,
        description = this.description,
        data = date
    )
}

fun List<MovieEntity>.toListMovieLogic(): List<MovieLogic> {
    val newListMovieUi = mutableListOf<MovieLogic>()
    this.forEach { movieEntity ->
        newListMovieUi.add(movieEntity.toMovieLogic())
    }
    return newListMovieUi
}