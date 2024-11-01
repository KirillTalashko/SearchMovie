package com.example.searchmovie.presentation.home.viewModel

import com.example.network.modelsMovie.Movie

sealed class MoviesMainFragmentState {
    data class Error(val error: String) : MoviesMainFragmentState()
    data class SuccessListMovie(val listMovie: List<Movie>, val isLoading: Boolean) :
        MoviesMainFragmentState()

    data object LoadingListMovie : MoviesMainFragmentState()

}