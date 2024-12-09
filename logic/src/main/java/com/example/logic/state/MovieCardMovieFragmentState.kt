package com.example.logic.state

import com.example.logic.model.MovieLogic

sealed class MovieCardMovieFragmentState {

    data object Error : MovieCardMovieFragmentState()
    data class SuccessMoviesRelated(val movies: List<MovieLogic>) : MovieCardMovieFragmentState()
    data object LoadingMoviesRelated : MovieCardMovieFragmentState()

}