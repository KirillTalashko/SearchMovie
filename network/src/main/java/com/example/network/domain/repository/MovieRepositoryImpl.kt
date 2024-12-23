package com.example.network.domain.repository

import com.example.network.domain.api.MovieApi
import com.example.network.modelsMovie.ListMovie
import com.example.network.modelsMovie.Movie
import retrofit2.Response

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getRandomMovie(): Response<Movie> {
        return movieApi.getRandomMovie()
    }

    override suspend fun getListMovie(
        limit: Int,
        page: Int,
        rating: String,
        genres: List<String>
    ): Response<ListMovie> {
        return movieApi.getListMovie(
            limit = limit,
            page = page,
            ratingKp = rating,
            genres = genres
        )
    }


}