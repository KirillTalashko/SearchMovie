package com.example.logic.state

import com.example.searchmovie.core.model.MovieUi

sealed class MovieMainFragmentState {
    data object Error : MovieMainFragmentState()
    data class SuccessMovie(val movieUi: MovieUi, val isLocalDate: Boolean) :
        MovieMainFragmentState()
    data object LoadingMovie : MovieMainFragmentState()

}