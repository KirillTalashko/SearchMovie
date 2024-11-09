package com.example.searchmovie.presentation.cardMovie.state

import com.example.network.modelsMovie.Movie

sealed class MovieCardMovieFragmentState {

    data class Error(val error: String) : MovieCardMovieFragmentState()
    data class SuccessMoviesRelated(val movies: List<Movie>) :
        MovieCardMovieFragmentState()

    data object LoadingMoviesRelated : MovieCardMovieFragmentState()

}