package com.example.searchmovie.presentation.home.useCase

import androidx.lifecycle.MutableLiveData
import com.example.searchmovie.presentation.home.viewModel.MovieMainFragmentState
import com.example.searchmovie.presentation.home.viewModel.MoviesMainFragmentState

interface MovieUseCase {

    fun getMovie()
    fun getListMovie()
    fun getStateResponseMovie(): MutableLiveData<MovieMainFragmentState>
    fun getStateResponseListMovie(): MutableLiveData<MoviesMainFragmentState>
    fun getTheListenerState(): Boolean
}