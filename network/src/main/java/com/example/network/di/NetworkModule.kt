package com.example.network.di

import com.example.network.domain.api.MovieApi
import com.example.network.domain.repository.MovieRepository
import com.example.network.domain.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
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
    @Named("key")
    fun interceptorMovieApiKey(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-KEY", apiKey)
                .build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkhttp(
        interceptor: Interceptor,
        @Named("key") interceptorMovieApiKey: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(interceptorMovieApiKey)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository {
        return MovieRepositoryImpl(movieApi)
    }

}

