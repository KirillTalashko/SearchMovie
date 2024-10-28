package com.example.searchmovie.presentation.home.viewModel

import com.example.network.modelsMovie.Movie

sealed class StateListMovieMainFragment {
    data class Error(val error: String) : StateListMovieMainFragment()
    data class SuccessListMovie(val listMovie: List<Movie>) : StateListMovieMainFragment()
    data object LoadingListMovie : StateListMovieMainFragment()

}