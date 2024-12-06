package com.example.logic.extension

import com.example.network.modelsMovie.Genres

fun List<String>?.toListGenres(): List<Genres> {
    val newGenreList = mutableListOf<Genres>()
    this?.forEach {
        newGenreList.add(Genres(it))
    }
    return newGenreList
}

fun List<Genres>?.toListString(): List<String> {
    val newListString = mutableListOf<String>()
    this?.forEach { genres ->
        genres.genresName?.let {
            newListString.add(it)
        }
    }
    return newListString
}