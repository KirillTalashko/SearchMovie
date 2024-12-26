package com.example.network.domain.api


import com.example.network.modelsMovie.Category
import com.example.network.modelsMovie.ListMovie
import com.example.network.modelsMovie.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("v1.4/movie/random")
    suspend fun getRandomMovie(@Query("rating.kp") ratingKp: String = "7-10"): Response<Movie>

    @GET("v1.4/movie")
    suspend fun getListMovie(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("rating.kp") ratingKp: String,
        @Query("genres.name") genres: List<String>,
        @Query("type") type: String? = null,
    ): Response<ListMovie>

    @GET("v1/movie/possible-values-by-field")
    suspend fun getCategoryMovie(
        @Query("field") field: String
    ): Response<List<Category>>
}