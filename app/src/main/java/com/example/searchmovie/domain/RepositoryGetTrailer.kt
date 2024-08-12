package com.example.searchmovie.domain

import com.example.searchmovie.model.RandomTrailerResponse

class RepositoryGetTrailer : MyRepository {
    override fun getTrailer(callback : (RandomTrailerResponse?, Throwable?) -> Unit){
        RetrofitGetApi().getResponse{ responce, throwable ->
            callback(responce,throwable)
        }
    }
}