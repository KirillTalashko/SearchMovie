package com.example.searchmovie.domain.api



import com.example.searchmovie.modelsMovie.Movie
import com.example.searchmovie.modelsMovie.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("v1.4/movie/random")
    suspend fun getRandomMovie(@Query("rating.kp") ratingKp: String = "7-10"): Response<Movie>

    @GET("v1.4/movie")
    suspend fun getListMovie(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int,
        @Query("rating.kp") ratingKp: String = "7-10"
    ): Response<MovieResponse>
}