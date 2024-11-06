package com.example.searchmovie.presentation.home.viewModel

import com.example.searchmovie.core.model.MovieUi

sealed class MoviesMainFragmentState {
    data class Error(val error: String) : MoviesMainFragmentState()
    data class SuccessListMovie(val listMovie: List<MovieUi>, val isLoading: Boolean) :
        MoviesMainFragmentState()

    data object LoadingListMovie : MoviesMainFragmentState()

}