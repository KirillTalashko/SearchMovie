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
import kotlinx.coroutines.withContext

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var _isLoading = false
    val isLoading: Boolean
        get() = _isLoading

    private val _state = MutableLiveData<StatusRequest>()
    val state: LiveData<StatusRequest>
        get() = _state

    init {
        getStatusResponse()
        getListMovieResponse()
    }

    fun getStatusResponse() {
        _state.value = StatusRequest.LoadingMovie
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomMovie()
                response.body()?.let {
                    _state.postValue(StatusRequest.SuccessMovie(it))
                }
                    ?: run { _state.postValue(StatusRequest.Error(NullPointerException().checkingResponse())) }
            } catch (e: Exception) {
                e.message?.log()
                _state.postValue(StatusRequest.Error(e.checkingResponse()))
            }
        }
    }

    fun getListMovieResponse() {
        if (!_isLoading) {
            "запрос".log()
            _isLoading = true
            _state.value = StatusRequest.LoadingListMovie
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repository.getListMovie()
                    }
                    response.body()?.let {
                        val currentList = it.movie.orEmpty()
                        _state.postValue(StatusRequest.SuccessListMovie(currentList))
                    }
                        ?: run { _state.postValue(StatusRequest.Error(NullPointerException().checkingResponse())) }
                } catch (e: Exception) {
                    _state.postValue(StatusRequest.Error(e.checkingResponse()))

                } finally {
                    _isLoading = false
                    "отработал".log()
                }
            }
        }
    }
}
