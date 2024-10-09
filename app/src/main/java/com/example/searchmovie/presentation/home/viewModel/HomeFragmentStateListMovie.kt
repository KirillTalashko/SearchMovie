package com.example.searchmovie.presentation.home.viewModel

import com.example.network.modelsMovie.Movie

sealed class HomeFragmentStateListMovie {
    data class Error(val error: String) : HomeFragmentStateListMovie()
    data class SuccessListMovie(val listMovie: List<Movie>): HomeFragmentStateListMovie()
    data object LoadingListMovie : HomeFragmentStateListMovie()

}