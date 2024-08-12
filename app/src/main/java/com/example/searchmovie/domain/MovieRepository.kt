package com.example.searchmovie.domain

import com.example.searchmovie.model.PosterMovie

interface MovieRepository {
    fun getRandomMovie(callback: (PosterMovie?, Throwable?) -> Unit)
}