package com.example.network.domain.repository

import com.example.network.domain.api.MovieApi
import com.example.network.modelsMovie.Movie
import com.example.network.modelsMovie.MovieResponse
import retrofit2.Response

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getRandomMovie(): Response<Movie> {
        return movieApi.getRandomMovie()
    }
    override suspend fun getListMovie(page:Int):Response<MovieResponse>{
        return movieApi.getListMovie(page = page)
    }

}