package com.example.network.domain.repository

import com.example.network.modelsMovie.ListMovie
import com.example.network.modelsMovie.Movie
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie(): Response<Movie>
    suspend fun getListMovie(page: Int): Response<ListMovie>

    suspend fun getListMovieByGenre(
        page: Int,
        rating: String = "5-10",
        genre: List<String>? = null,
    ): Response<ListMovie>
}