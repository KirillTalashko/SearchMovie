package com.example.logic.extension

import com.example.database.modelEntity.MovieEntity
import com.example.logic.model.GenreLogic
import com.example.logic.model.MovieLogic
import com.example.logic.model.PosterLogic
import com.example.logic.model.RatingLogic

fun MovieEntity.toMovieLogic(): MovieLogic {
    return MovieLogic(
        id = this.idMovieKp,
        name = this.name,
        rating = this.toRatingLogic(),
        poster = this.toPosterLogic(),
        duration = this.duration,
        year = this.year,
        genres = this.genres.toListGenreLogic { GenreLogic(it) },
        type = this.type,
        description = this.description,
        data = date
    )
}

fun MovieEntity.toRatingLogic(): RatingLogic {
    return RatingLogic(
        kp = this.ratingKp,
        imd = this.ratingIMDb
    )
}

fun MovieEntity.toPosterLogic(): PosterLogic {
    return PosterLogic(
        url = this.url
    )
}

fun List<MovieEntity>.toListMovieLogic(): List<MovieLogic> {
    val newListMovieUi = mutableListOf<MovieLogic>()
    this.forEach { movieEntity ->
        newListMovieUi.add(movieEntity.toMovieLogic())
    }
    return newListMovieUi
}