package com.example.searchmovie.presentation.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.presentation.home.state.MovieMainFragmentState
import com.example.searchmovie.presentation.home.state.MoviesMainFragmentState
import com.example.searchmovie.presentation.home.useCase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelRandomMovie @Inject constructor(
    private val useCase: MovieUseCase
) : ViewModel() {

    fun getIsLoading() = useCase.getListenerLoadingMovie()
    val stateRandomMovie: LiveData<MovieMainFragmentState>
        get() = useCase.getMovieState().asLiveData()

    val stateListMovie: LiveData<MoviesMainFragmentState>
        get() = useCase.getMoviesState().asLiveData()

    init {
        getMovie()
        getMovies()
    }
    fun getMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMovie()
        }
    }

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMovies()
        }
    }
}
