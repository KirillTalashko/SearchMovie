package com.example.searchmovie.presentation.home.useCase

import androidx.lifecycle.MutableLiveData
import com.example.searchmovie.presentation.home.viewModel.MoviesMainFragmentState
import com.example.searchmovie.presentation.home.viewModel.StateRandomMovieMainFragment

interface MovieUseCase {

    fun getMovie()
    fun getListMovie()
    fun getStateResponseMovie(): MutableLiveData<StateRandomMovieMainFragment>
    fun getStateResponseListMovie(): MutableLiveData<MoviesMainFragmentState>
    fun getTheListenerState(): Boolean

}