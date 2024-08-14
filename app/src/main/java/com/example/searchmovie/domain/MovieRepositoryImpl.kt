package com.example.searchmovie.domain

import com.example.searchmovie.model.PosterMovie
import retrofit2.Response

class MovieRepositoryImpl : MovieRepository {

    override suspend fun getRandomMovie(): Response<PosterMovie> {
        return RetrofitGetApi().createMovieApi().getRandomMovie()
    }

}