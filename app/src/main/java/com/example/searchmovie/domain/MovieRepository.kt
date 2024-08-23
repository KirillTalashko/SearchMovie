package com.example.searchmovie.domain

import com.example.searchmovie.model.Movie
import com.example.searchmovie.model.MovieResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie() : Response<Movie>
    suspend fun getListMovie(): Response<MovieResponse>
}