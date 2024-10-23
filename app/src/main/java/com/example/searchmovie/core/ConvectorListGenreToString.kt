package com.example.searchmovie.core

import com.example.network.modelsMovie.Genres

class ConvectorListGenreToString {

    companion object {
        fun mapperGenre(genre: List<Genres>?): List<String> {
            val newList = mutableListOf<String>()
            genre?.forEach { genres ->
                genres.genresName?.let {
                    newList.add(it)
                }
            }
            return newList
        }
    }
}