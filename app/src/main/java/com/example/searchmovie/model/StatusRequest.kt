package com.example.searchmovie.model

sealed class StatusRequest {
    data class Error(val error: String) : StatusRequest()
    data class Success(val data: Movie) : StatusRequest()
    data object Loading: StatusRequest()
}