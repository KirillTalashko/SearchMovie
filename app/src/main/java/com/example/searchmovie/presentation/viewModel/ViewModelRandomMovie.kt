package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.extension.checkingResponse
import com.example.searchmovie.extension.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    var isLoading = false

    private val _state = MutableLiveData<StatusRequest>()
    val state: LiveData<StatusRequest>
        get() = _state

    init {
        getStatusResponse()
        getListMovieResponse()
    }

    fun getStatusResponse() {
        _state.postValue(StatusRequest.LoadingMovie)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomMovie()
                response.body()?.let {
                    _state.postValue(StatusRequest.SuccessMovie(it))
                    isLoading = false
                }
                    ?: run { _state.postValue(StatusRequest.Error(NullPointerException().checkingResponse())) }
            } catch (e: Exception) {
                e.message?.log()
                _state.postValue(StatusRequest.Error(e.checkingResponse()))
            }
        }
    }

    private fun getListMovieResponse() {
        _state.postValue(StatusRequest.LoadingListMovie)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getListMovie()
                response.body()?.let {
                    val currentList = it.movie.orEmpty()
                    val updatedList = it.movie?.plus(currentList) ?: emptyList()
                    isLoading = false
                    _state.postValue(StatusRequest.SuccessListMovie(updatedList))
                }
                    ?: run { _state.postValue(StatusRequest.Error(NullPointerException().checkingResponse())) }
            } catch (e: Exception) {
                _state.postValue(StatusRequest.Error(e.checkingResponse()))

            }
        }
    }
}
