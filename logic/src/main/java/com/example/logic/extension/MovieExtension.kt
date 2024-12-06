package com.example.logic.extension

import com.example.database.modelEntity.MovieEntity
import com.example.logic.model.MovieLogic
import com.example.network.modelsMovie.Movie
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = 0,
        idMovieKp = this.id,
        name = this.name ?: "Нет названия",
        url = this.poster?.url,
        ratingIMDb = this.rating.imd,
        ratingKp = this.rating.kp,
        duration = this.duration,
        year = this.year,
        genres = this.genres.toListString(),
        type = this.type,
        description = this.description ?: "Описание к фильму отсутствует"
    )
}

fun Movie.toMovieLogic(): MovieLogic {
    return MovieLogic(
        id = this.id,
        name = this.name,
        poster = Poster(this.poster?.url),
        rating = Rating(this.rating.kp, this.rating.imd),
        duration = this.duration,
        year = this.year,
        genres = this.genres,
        type = this.type,
        description = this.description,
        data = null
    )
}

fun List<Movie>.toListMovieLogic(): List<MovieLogic> {
    val newListMovieUi = mutableListOf<MovieLogic>()
    this.forEach { movie ->
        newListMovieUi.add(movie.toMovieLogic())
    }
    return newListMovieUi
}