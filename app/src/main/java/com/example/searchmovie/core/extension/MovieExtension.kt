package com.example.searchmovie.core.extension

import com.example.database.modelEntity.MovieEntity
import com.example.network.modelsMovie.Movie
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating
import com.example.searchmovie.core.model.MovieUi

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

fun Movie.toMovieUi(): MovieUi {
    return MovieUi(
        id = this.id,
        name = this.name,
        poster = Poster(this.poster?.url),
        rating = Rating(this.rating.kp, this.rating.imd),
        duration = this.duration,
        year = this.year,
        genres = this.genres,
        type = this.type,
        description = this.description
    )
}

fun MovieUi.toMovie(): Movie {
    return Movie(
        id = this.id,
        name = this.name,
        poster = Poster(this.poster?.url),
        rating = Rating(this.rating.kp, this.rating.imd),
        duration = this.duration,
        year = this.year,
        genres = this.genres,
        type = this.type,
        description = this.description
    )
}

fun List<Movie>.toListMovieUi(): List<MovieUi> {
    val newListMovieUi = mutableListOf<MovieUi>()
    this.forEach { movie ->
        newListMovieUi.add(movie.toMovieUi())
    }
    return newListMovieUi
}