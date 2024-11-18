package com.example.searchmovie.presentation.main.state

import com.example.common.utils.DataErrorOrigin
import com.example.searchmovie.core.model.MovieUi

sealed class MoviesMainFragmentState {
    data class Error(val dataErrorOrigin: DataErrorOrigin) : MoviesMainFragmentState()
    data class SuccessListMovie(val listMovie: List<MovieUi>, val isLocalData: Boolean) :
        MoviesMainFragmentState()

    data object LoadingListMovie : MoviesMainFragmentState()

}