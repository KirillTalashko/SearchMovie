package com.example.searchmovie.domain

import com.example.searchmovie.model.Movie
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie() : Response<Movie>
}