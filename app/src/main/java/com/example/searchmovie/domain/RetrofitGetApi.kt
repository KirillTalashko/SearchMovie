package com.example.searchmovie.domain

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitGetApi {
    private val apiKey = "T89S8JR-Y5343QN-HCRHHVV-KATN0A8"
    private val baseUrl = "https://api.kinopoisk.dev/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(InterceptorMovieApiKey(apiKey))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor {chain ->
            var response: Response? = null
            var lastError: Exception? = null
            repeat(3){
                try {
                    response = chain.proceed(chain.request())
                    if (response!!.isSuccessful){
                        return@addInterceptor response!!
                    }
                }catch (e:Exception){
                    lastError = e
                }
            }
            response ?:throw lastError ?: throw IOException("Ошибка получения данных")
        }
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