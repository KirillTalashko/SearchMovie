package com.example.searchmovie.presentation.main.state

import com.example.searchmovie.core.model.MovieUi

sealed class MoviesMainFragmentState {
    data object Error : MoviesMainFragmentState()
    data class SuccessListMovie(val listMovie: List<MovieUi>, val isLocalData: Boolean) :
        MoviesMainFragmentState()

    data object LoadingListMovie : MoviesMainFragmentState()

}