package com.example.searchmovie.domain



import com.example.searchmovie.model.RandomTrailerResponse
import retrofit2.Call
import retrofit2.http.GET

interface RandomApi {
    @GET("v1.4/movie/random")
    fun getRandomMovie(): Call<RandomTrailerResponse>
}