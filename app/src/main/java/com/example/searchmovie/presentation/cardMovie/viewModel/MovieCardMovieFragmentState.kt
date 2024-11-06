package com.example.searchmovie.presentation.cardMovie.viewModel

import com.example.network.modelsMovie.Movie

sealed class MovieCardMovieFragmentState {

    data class Error(val error: String) : MovieCardMovieFragmentState()
    data class SuccessRelatedMovies(val listMovie: List<Movie>) :
        MovieCardMovieFragmentState()

    data object LoadingRelatedMovies : MovieCardMovieFragmentState()

}