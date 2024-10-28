package com.example.network.domain.repository

import com.example.network.modelsMovie.ListMovie
import com.example.network.modelsMovie.Movie
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie(): Response<Movie>
    suspend fun getListMovie(
        page: Int,
        rating: String,
        genres: List<String>
    ): Response<ListMovie>
}