package com.example.searchmovie.presentation.home.useCase

import com.example.searchmovie.presentation.home.state.MovieMainFragmentState
import com.example.searchmovie.presentation.home.state.MoviesMainFragmentState
import kotlinx.coroutines.flow.MutableStateFlow

interface MovieUseCase {

    suspend fun getMovie()
    suspend fun getMovies()
    fun getMovieState(): MutableStateFlow<MovieMainFragmentState>
    fun getMoviesState(): MutableStateFlow<MoviesMainFragmentState>
    fun getListenerLoadingMovie(): Boolean
}