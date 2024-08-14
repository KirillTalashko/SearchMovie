package com.example.searchmovie.domain

import com.example.searchmovie.model.PosterMovie
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie() : Response<PosterMovie>
}