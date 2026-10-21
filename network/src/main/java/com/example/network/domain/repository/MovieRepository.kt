package com.example.network.domain.repository

import com.example.network.modelsMovie.Category
import com.example.network.modelsMovie.ListMovie
import com.example.network.modelsMovie.Movie
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie(): Response<Movie>
    suspend fun getListMovie(
        limit: Int,
        page: Int,
        rating: String,
        genres: List<String>,
        type: String? = null
    ): Response<ListMovie>

    suspend fun getFilteringOptions(field: String): Response<List<Category>>
}