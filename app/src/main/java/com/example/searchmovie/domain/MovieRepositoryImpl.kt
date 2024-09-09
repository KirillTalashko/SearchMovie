package com.example.searchmovie.domain

import com.example.searchmovie.model.Movie
import com.example.searchmovie.model.MovieResponse
import retrofit2.Response

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {

    override suspend fun getRandomMovie(): Response<Movie> {
        return movieApi.getRandomMovie()
    }
    override suspend fun getListMovie(page:Int):Response<MovieResponse>{
        return movieApi.getListMovie(page = page)
    }

}