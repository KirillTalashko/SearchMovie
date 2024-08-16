package com.example.searchmovie.domain



import com.example.searchmovie.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET("v1.4/movie/random")
    suspend fun getRandomMovie(): Response<Movie>
}