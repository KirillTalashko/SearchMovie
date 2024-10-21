package com.example.searchmovie.presentation.cardMovie.viewModel

import com.example.network.modelsMovie.Movie

sealed class CardMovieFragmentStateRelatedMovies {

    data class Error(val error: String) : CardMovieFragmentStateRelatedMovies()
    data class SuccessRelatedMovies(val listMovie: List<Movie>) :
        CardMovieFragmentStateRelatedMovies()

    data object LoadingRelatedMovies : CardMovieFragmentStateRelatedMovies()

}