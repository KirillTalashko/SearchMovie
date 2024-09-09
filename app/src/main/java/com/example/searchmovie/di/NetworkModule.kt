package com.example.searchmovie.di

import com.example.searchmovie.domain.InterceptorMovieApiKey
import com.example.searchmovie.domain.MovieApi
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.domain.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private val apiKey = "T89S8JR-Y5343QN-HCRHHVV-KATN0A8"
    private val baseUrl = "https://api.kinopoisk.dev/"

    @Singleton
    @Provides
    fun interceptor(): Interceptor {
        return Interceptor { chain ->
            var response: Response? = null
            var lastError: Exception? = null
            repeat(3) {
                try {
                    response = chain.proceed(chain.request())
                    if (response!!.isSuccessful) {
                        return@Interceptor response!!
                    }
                } catch (e: Exception) {
                    lastError = e
                }
            }
            response ?: throw lastError ?: throw IOException("Ошибка получения данных")
        }
    }

    @Singleton
    @Provides
    fun provideOkhttp(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(InterceptorMovieApiKey(apiKey))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(interceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return  Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi{
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi) : MovieRepository{
        return MovieRepositoryImpl(movieApi)
    }

}

