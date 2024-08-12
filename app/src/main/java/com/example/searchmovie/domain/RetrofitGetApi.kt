package com.example.searchmovie.domain

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitGetApi {
    private val apiKey = "XMM6YTT-2EP4J36-KVCC1HQ-JAKVEF6"
    private val baseUrl = "https://api.kinopoisk.dev/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(InterceptorMovieApiKey(apiKey))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun createMovieApi(): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}