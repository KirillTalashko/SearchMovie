package com.example.network.domain.repository

import com.example.network.domain.api.MovieApi
import com.example.network.modelsMovie.ListMovie
import com.example.network.modelsMovie.Movie
import retrofit2.Response

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getRandomMovie(): Response<Movie> {
        return movieApi.getRandomMovie()
    }

    override suspend fun getListMovie(page: Int): Response<ListMovie> {
        return movieApi.getListMovie(page = page)
    }

    override suspend fun getListMovieByGenre(
        page: Int,
        rating: String,
        genre: List<String>?
    ): Response<ListMovie> {
        return movieApi.getListMovieByGenre(
            page = page,
            ratingKp = rating,
            genre = genre
        )
    }

}