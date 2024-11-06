package com.example.searchmovie.presentation.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.searchmovie.presentation.home.useCase.MovieUseCase
import javax.inject.Inject

class ViewModelRandomMovie @Inject constructor(
    private val useCase: MovieUseCase
) : ViewModel() {
    fun getIsLoading() = useCase.getTheListenerState()
    val stateRandomMovie: LiveData<MovieMainFragmentState>
        get() = useCase.getStateResponseMovie()

    val stateListMovie: LiveData<MoviesMainFragmentState>
        get() = useCase.getStateResponseListMovie()

    init {
        getRandomMovie()
        getListMovie()
    }

    fun getRandomMovie() {
        useCase.getMovie()
    }

    fun getListMovie() {
        useCase.getListMovie()
    }
}
