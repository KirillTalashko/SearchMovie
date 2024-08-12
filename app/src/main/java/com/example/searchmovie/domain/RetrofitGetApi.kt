package com.example.searchmovie.domain

import com.example.searchmovie.model.RandomTrailerResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RetrofitGetApi {
    private val apiKey = "XMM6YTT-2EP4J36-KVCC1HQ-JAKVEF6"
    private var retrofit: Retrofit? = null
    private val baseUrl = "https://api.kinopoisk.dev/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(MyInterceptor(apiKey))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private fun getRandomMovie(): RandomApi {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(RandomApi::class.java)
    }

    fun getResponse(callback : (RandomTrailerResponse?, Throwable?) -> Unit){
        getRandomMovie().getRandomMovie().enqueue(object : Callback<RandomTrailerResponse> {
            override fun onResponse(
                call: Call<RandomTrailerResponse>,
                response: Response<RandomTrailerResponse>
            ) {
                if (!response.isSuccessful) {
                    callback(null, Exception("Unexpected code $response"))
                        throw IOException(
                            "Запрос к серверу не был успешен"
                        )
                    }
                    callback(response.body(), null)
            }

            override fun onFailure(call: Call<RandomTrailerResponse>, t: Throwable) {
                t.printStackTrace()
                callback(null,t)
            }
        })
    }
}