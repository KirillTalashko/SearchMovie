package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.extension.checkingResponse
import com.example.searchmovie.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var _mediatorData = MediatorLiveData<DetailsData>()
    private var _state = MutableLiveData<StatusRequest>(StatusRequest.Loading)
    private var _movie = MutableLiveData<Movie>()
    private var _listMovie = MutableLiveData<List<Movie>>()
    val state: LiveData<StatusRequest>
        get() = _state
    val movie: LiveData<Movie>
        get() = _movie
    val listMovie: LiveData<List<Movie>>
        get() = _listMovie
    val mediator: MediatorLiveData<DetailsData>
        get() = _mediatorData

    init {
        _mediatorData.addSource(_movie) {
            _mediatorData.value?.movie = it
        }
        _mediatorData.addSource(_listMovie) {
            _mediatorData.value?.movieList = it
        }
        getStatusResponse()
        getListMovieResponse()
    }

    fun getStatusResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getRandomMovie()
            } catch (e: Exception) {
                throw e
            }
            response.body()?.let {
                _movie.postValue(it)
            } ?: throw NullPointerException()
        }
    }
    private fun getListMovieResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getListMovie()
            } catch (e: Exception) {
               throw e
            }
            val newMovies = response.body()?.movie ?: emptyList()
            val updatedList = response.body()?.movie.orEmpty() + newMovies
            _listMovie.postValue(updatedList)
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