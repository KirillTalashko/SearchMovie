package com.example.searchmovie.presentation.home.viewModel

import com.example.network.modelsMovie.Movie

sealed class HomeFragmentStateRandomMovie {
    data class Error(val error: String) : HomeFragmentStateRandomMovie()
    data class SuccessMovie(val movie: Movie) : HomeFragmentStateRandomMovie()
    data object LoadingMovie : HomeFragmentStateRandomMovie()

}