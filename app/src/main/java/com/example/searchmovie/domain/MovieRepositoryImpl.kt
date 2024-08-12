package com.example.searchmovie.domain

import com.example.searchmovie.model.PosterMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieRepositoryImpl : MovieRepository {
    override fun getRandomMovie(callback: (PosterMovie?, Throwable?) -> Unit) {
        RetrofitGetApi().createMovieApi().getRandomMovie()
            .enqueue(object : Callback<PosterMovie> {
                override fun onResponse(
                    call: Call<PosterMovie>,
                    response: Response<PosterMovie>
                ) {
                    if (!response.isSuccessful) {
                        callback(null, Exception("Unexpected code $response"))
                        throw IOException(
                            "Запрос к серверу не был успешен"
                        )
                    }
                    callback(response.body(), null)
                }

                override fun onFailure(call: Call<PosterMovie>, t: Throwable) {
                    t.printStackTrace()
                    callback(null, t)
                }

            })
    }
}