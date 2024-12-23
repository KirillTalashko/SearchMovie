package com.example.logic.extension

import com.example.database.modelEntity.MovieEntity
import com.example.logic.model.GenreLogic
import com.example.logic.model.MovieLogic
import com.example.network.modelsMovie.Movie

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
        genres = this.genres.toListString { GenreLogic(it.genresName) },
        type = this.type,
        description = this.description ?: "Описание к фильму отсутствует"
    )
}

fun Movie.toMovieLogic(): MovieLogic {
    return MovieLogic(
        id = this.id,
        name = this.name,
        poster = this.poster?.toPosterLogic(),
        rating = this.rating.toRatingLogic(),
        duration = this.duration,
        year = this.year,
        genres = this.genres.toListGenreLogic { GenreLogic(it.genresName) },
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