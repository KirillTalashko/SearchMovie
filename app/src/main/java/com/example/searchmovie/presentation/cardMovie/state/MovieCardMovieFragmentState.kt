package com.example.searchmovie.presentation.cardMovie.state

import com.example.searchmovie.core.model.MovieUi

sealed class MovieCardMovieFragmentState {

    data object Error : MovieCardMovieFragmentState()
    data class SuccessMoviesRelated(val movies: List<MovieUi>) : MovieCardMovieFragmentState()
    data object LoadingMoviesRelated : MovieCardMovieFragmentState()

}