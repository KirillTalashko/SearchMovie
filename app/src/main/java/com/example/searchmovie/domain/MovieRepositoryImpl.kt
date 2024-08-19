package com.example.searchmovie.domain

import com.example.searchmovie.model.ListMovie
import com.example.searchmovie.model.Movie
import retrofit2.Response

class MovieRepositoryImpl : MovieRepository {

    override suspend fun getRandomMovie(): Response<Movie> {
        return RetrofitGetApi().createMovieApi().getRandomMovie()
    }
    override suspend fun getListMovie():Response<ListMovie>{
        return RetrofitGetApi().createMovieApi().getListMovie()
    }

}