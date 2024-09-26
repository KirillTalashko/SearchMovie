package com.example.searchmovie.domain.repositopy

import com.example.searchmovie.domain.api.MovieApi
import com.example.searchmovie.modelsMovie.Movie
import com.example.searchmovie.modelsMovie.MovieResponse
import retrofit2.Response

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getRandomMovie(): Response<Movie> {
        return movieApi.getRandomMovie()
    }
    override suspend fun getListMovie(page:Int):Response<MovieResponse>{
        return movieApi.getListMovie(page = page)
    }

}