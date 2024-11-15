package com.example.searchmovie.presentation.main.state

import com.example.searchmovie.core.model.MovieUi

sealed class MovieMainFragmentState {
    data class Error(val error: String) : MovieMainFragmentState()
    data class SuccessMovie(val movie: MovieUi) : MovieMainFragmentState()
    data object LoadingMovie : MovieMainFragmentState()

}