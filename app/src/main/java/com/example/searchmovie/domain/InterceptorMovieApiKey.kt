package com.example.searchmovie.domain

import okhttp3.Interceptor
import okhttp3.Response

class InterceptorMovieApiKey(private  val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", apiKey)
            .build()
        return chain.proceed(request)
    }
}