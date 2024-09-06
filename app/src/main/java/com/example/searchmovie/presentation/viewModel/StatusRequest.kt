package com.example.searchmovie.presentation.viewModel

import com.example.searchmovie.model.Movie

sealed class StatusRequest {
    data class Error(val error: String) : StatusRequest()
    data class SuccessMovie(val movie: Movie) : StatusRequest()
    data class SuccessListMovie(val listMovie: List<Movie>): StatusRequest()
    data object LoadingMovie : StatusRequest()
    data object LoadingListMovie : StatusRequest()
}