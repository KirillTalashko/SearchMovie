package com.example.searchmovie.core.extension

import com.example.database.modelEntity.MovieEntity
import com.example.network.modelsMovie.Movie

fun Movie.mapperInMovieEntity(): MovieEntity {
    return MovieEntity(
        id = 0,
        idMovieKp = this.id,
        name = this.name ?: "Нет названия",
        url = this.poster?.url,
        ratingIMDb = this.rating.imd,
        ratingKp = this.rating.kp,
        duration = this.duration,
        year = this.year,
        genres = this.genres.mapperInListString(),
        type = this.type,
        description = this.description ?: "Описание к фильму отсутствует"
    )
}