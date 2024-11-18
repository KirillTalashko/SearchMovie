package com.example.searchmovie.presentation.main.state

import com.example.common.utils.DataErrorOrigin
import com.example.searchmovie.core.model.MovieUi

sealed class MovieMainFragmentState {
    data class Error(val dataErrorOrigin: DataErrorOrigin) : MovieMainFragmentState()
    data class SuccessMovie(val movie: MovieUi, val isLocalDate: Boolean) : MovieMainFragmentState()
    data object LoadingMovie : MovieMainFragmentState()

}