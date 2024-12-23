package com.example.logic.state

import com.example.logic.model.MovieLogic

sealed class MoviesMainFragmentState {
    data object Error : MoviesMainFragmentState()
    data class SuccessListMovie(val listMovieLogic: List<MovieLogic>, val isLocalData: Boolean) :
        MoviesMainFragmentState()

    data object LoadingListMovie : MoviesMainFragmentState()

}