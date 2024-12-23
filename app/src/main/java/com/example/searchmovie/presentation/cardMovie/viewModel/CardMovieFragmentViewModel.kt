package com.example.searchmovie.presentation.cardMovie.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logic.state.MovieCardMovieFragmentState
import com.example.searchmovie.presentation.modelMovie.MovieUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardMovieFragmentViewModel(private val useCase: com.example.logic.useCase.MovieCardUseCase) :
    ViewModel() {

    fun getIsLoading() = useCase.isLoading

    val stateMoviesByGenre: LiveData<MovieCardMovieFragmentState>
        get() = useCase.stateMoviesByGenre

    val trackingReadMore: LiveData<Boolean>
        get() = useCase.trackingReadMore

    fun onReadMoreClicked() {
        viewModelScope.launch {
            useCase.trackingReadMore.postValue(true)
        }
    }

    fun onLessMoreClicked() {
        viewModelScope.launch {
            useCase.trackingReadMore.postValue(false)
        }
    }

    fun getMovies(movie: MovieUi) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMoviesByGenreLocal(movie)
        }
    }
}