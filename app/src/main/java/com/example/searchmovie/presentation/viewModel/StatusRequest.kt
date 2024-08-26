package com.example.searchmovie.presentation.viewModel

import com.example.searchmovie.model.Movie

sealed class StatusRequest {
    data class Error(val error: String) : StatusRequest()
    data class Success(val data: Movie) : StatusRequest()
    data object Loading: StatusRequest()
}