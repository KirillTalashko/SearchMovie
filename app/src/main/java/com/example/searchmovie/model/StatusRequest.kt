package com.example.searchmovie.model

sealed class StatusRequest {
    data class Error(val error: Throwable) : StatusRequest()
    data class Success(val data: PosterMovie) : StatusRequest()
    data object Loading: StatusRequest()
}