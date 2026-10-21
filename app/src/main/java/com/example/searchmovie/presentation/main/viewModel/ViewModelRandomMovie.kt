package com.example.searchmovie.presentation.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.state.MovieMainFragmentState
import com.example.domain.state.MoviesMainFragmentState
import com.example.domain.useCase.MovieMainUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelRandomMovie @Inject constructor(
    private val useCase: MovieMainUseCase
) : ViewModel() {

    fun getIsLoading() = useCase.isLoading
    val stateRandomMovie: LiveData<MovieMainFragmentState>
        get() = useCase.stateRandomMovie.asLiveData()

    val stateListMovie: LiveData<MoviesMainFragmentState>
        get() = useCase.stateListMovie.asLiveData()


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

    fun getLocalMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMovieLocal()
        }
    }

    fun getLocalMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMoviesLocal()
        }
    }
}
