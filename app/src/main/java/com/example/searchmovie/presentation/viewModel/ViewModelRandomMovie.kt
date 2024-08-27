package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.extension.checkingResponse
import com.example.searchmovie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var _mediatorData = MediatorLiveData<DetailsData>()
    private var _movie = MutableLiveData<Movie>()
    private var _listMovie = MutableLiveData<List<Movie>>()
    private var _movieError = MutableLiveData<String>()
    private var _listMovieError = MutableLiveData<String>()
    val mediator: MediatorLiveData<DetailsData>
        get() = _mediatorData

    init {
        _mediatorData.addSource(_movie) {
            _mediatorData.value = _mediatorData.value?.copy(movie = it)
        }
        _mediatorData.addSource(_listMovie) {
            _mediatorData.value = _mediatorData.value?.copy(listMovie = it)
        }
        _mediatorData.addSource(_movieError) {
            _mediatorData.value = _mediatorData.value?.copy(movieError = it)
        }
        _mediatorData.addSource(_listMovieError) {
            _mediatorData.value = _mediatorData.value?.copy(movieListError = it)
        }
        getStatusResponse()
        getListMovieResponse()
    }

    fun getStatusResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomMovie()
                if (response.body() == null) {
                    _movieError.postValue(NullPointerException().checkingResponse())
                    return@launch
                }
                _movie.postValue(response.body())
            } catch (e: Exception) {
                _movieError.postValue(e.checkingResponse())
            }
        }
    }

    private fun getListMovieResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getListMovie()
                if (response.body() == null) {
                    _listMovieError.postValue(NullPointerException().checkingResponse())
                    return@launch
                }
                val newMovies = response.body()?.movie ?: emptyList()
                val updatedList = response.body()?.movie.orEmpty() + newMovies
                _listMovie.postValue(updatedList)
            } catch (e: Exception) {
                _listMovieError.postValue(e.checkingResponse())
            }
        }
    }
}

/*fun getStatusResponse() {
    _state.postValue(StatusRequest.Loading)
    viewModelScope.launch(Dispatchers.IO) {
        val response = try {
            repository.getRandomMovie()
        } catch (e: Exception) {
            _state.postValue(StatusRequest.Error(e.checkingResponse()))
            return@launch
        }
        response.body()?.let {
            _state.postValue(StatusRequest.Success(it))
        } ?: _state.postValue(StatusRequest.Error(NullPointerException().checkingResponse()))
    }
}*/

/*private fun getListMovieResponse() {
    viewModelScope.launch(Dispatchers.IO) {
        val response = try {
            repository.getListMovie()
        } catch (e: Exception) {
            _state.postValue(StatusRequest.Error(e.checkingResponse()))
            return@launch
        }
        val newMovies = response.body()?.movie ?: emptyList()
        val updatedList = response.body()?.movie.orEmpty() + newMovies
        _listMovie.postValue(updatedList)
    }
}*/