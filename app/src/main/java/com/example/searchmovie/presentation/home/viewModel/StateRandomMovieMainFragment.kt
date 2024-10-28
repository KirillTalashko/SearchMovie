package com.example.searchmovie.presentation.home.viewModel

import com.example.network.modelsMovie.Movie

sealed class StateRandomMovieMainFragment {
    data class Error(val error: String) : StateRandomMovieMainFragment()
    data class SuccessMovie(val movie: Movie) : StateRandomMovieMainFragment()
    data object LoadingMovie : StateRandomMovieMainFragment()

}