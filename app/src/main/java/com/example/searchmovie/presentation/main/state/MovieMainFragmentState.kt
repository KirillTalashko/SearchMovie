package com.example.searchmovie.presentation.main.state

import com.example.searchmovie.core.model.MovieUi

sealed class MovieMainFragmentState {
    data object Error : MovieMainFragmentState()
    data class SuccessMovie(val movie: MovieUi, val isLocalDate: Boolean) : MovieMainFragmentState()
    data object LoadingMovie : MovieMainFragmentState()

}