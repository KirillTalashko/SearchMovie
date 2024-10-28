package com.example.searchmovie.core.extension

import com.example.database.modelEntity.MovieEntity
import com.example.network.modelsMovie.Movie
import com.example.network.modelsMovie.Poster
import com.example.network.modelsMovie.Rating

fun MovieEntity.mapperInMovie(): Movie {
    return Movie(
        id = this.idMovieKp,
        name = this.name,
        poster = Poster(this.url),
        rating = Rating(this.ratingKp, this.ratingIMDb),
        duration = this.duration,
        year = this.year,
        genres = this.genres.mapperInListGenre(),
        type = this.type,
        description = this.description
    )
}

fun List<MovieEntity>.mapperInListMovie(): List<Movie> {
    val newListMovie = mutableListOf<Movie>()
    this.forEach {
        newListMovie.add(it.mapperInMovie())
    }
    return newListMovie
}