package com.example.searchmovie.domain



import com.example.searchmovie.model.PosterMovie
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    @GET("v1.4/movie/random")
    fun getRandomMovie(): Call<PosterMovie>
}