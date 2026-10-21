package com.example.domain.state

import com.example.domain.model.MovieLogic

sealed class MovieCardMovieFragmentState {

    data object Error : MovieCardMovieFragmentState()
    data class SuccessMoviesRelated(val movies: List<MovieLogic>) : MovieCardMovieFragmentState()
    data object LoadingMoviesRelated : MovieCardMovieFragmentState()

}