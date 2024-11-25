package com.example.searchmovie.presentation.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.presentation.main.state.MovieMainFragmentState
import com.example.searchmovie.presentation.main.state.MoviesMainFragmentState
import com.example.searchmovie.presentation.main.useCase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ViewModelRandomMovie @Inject constructor(
    private val useCase: MovieUseCase
) : ViewModel() {

    fun getIsLoading() = useCase.isLoading
    val stateRandomMovie: LiveData<MovieMainFragmentState>
        get() = useCase.stateRandomMovie.asLiveData()

    val stateListMovie: LiveData<MoviesMainFragmentState>
        get() = useCase.stateListMovie.asLiveData()

    init {
        getMovie(firstLaunch = true, waitingForConnection = false)
        getMovies(firstLaunch = true, waitingForConnection = false)
    }

    fun getMovie(firstLaunch: Boolean, waitingForConnection: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMovie(firstLaunch, waitingForConnection)
        }
    }

    fun getMovies(firstLaunch: Boolean, waitingForConnection: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMovies(firstLaunch, waitingForConnection)
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
