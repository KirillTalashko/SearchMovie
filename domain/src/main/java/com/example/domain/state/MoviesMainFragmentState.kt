package com.example.domain.state

import com.example.domain.model.MovieLogic

sealed class MoviesMainFragmentState {

    data object Error : MoviesMainFragmentState()
    data class SuccessListMovie(val listMovieLogic: List<MovieLogic>, val isLocalData: Boolean) :
        MoviesMainFragmentState()

    data object LoadingListMovie : MoviesMainFragmentState()

}