package com.example.searchmovie.domain.repositopy

import com.example.searchmovie.modelsMovie.Movie
import com.example.searchmovie.modelsMovie.MovieResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getRandomMovie() : Response<Movie>
    suspend fun getListMovie(page:Int): Response<MovieResponse>
}