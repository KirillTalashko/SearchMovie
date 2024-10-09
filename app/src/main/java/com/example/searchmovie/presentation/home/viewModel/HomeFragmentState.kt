package com.example.searchmovie.presentation.home.viewModel

import com.example.network.modelsMovie.Movie

sealed class HomeFragmentState {
    data class Error(val error: String) : HomeFragmentState()
    data class SuccessMovie(val movie: Movie) : HomeFragmentState()
    data class SuccessListMovie(val listMovie: List<Movie>): HomeFragmentState()
    data object LoadingMovie : HomeFragmentState()
    data object LoadingListMovie : HomeFragmentState()
}