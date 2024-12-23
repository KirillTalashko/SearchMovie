package com.example.logic.state

import com.example.logic.model.MovieLogic

sealed class MovieMainFragmentState {
    data object Error : MovieMainFragmentState()
    data class SuccessMovie(val movieLogic: MovieLogic, val isLocalDate: Boolean) :
        MovieMainFragmentState()

    data object LoadingMovie : MovieMainFragmentState()

}