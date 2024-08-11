package com.example.searchmovie.domain

import com.example.searchmovie.model.RandomTrailerResponse

interface MyRepository {
    fun getTrailer(callback : (RandomTrailerResponse?, Throwable?) -> Unit)
}