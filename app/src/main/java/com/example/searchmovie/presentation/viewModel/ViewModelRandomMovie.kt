package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.extension.checkingResponse
import com.example.searchmovie.model.MovieResponse
import com.example.searchmovie.model.StatusRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private val coroutineContext = (Dispatchers.IO) + Job()
    private var scopeImp: CoroutineScope = CoroutineScope(coroutineContext)
    private var jobResponseMovie: Job? = null
    private var jobRepeatRequest: Job? = null

    private var _movie = MutableLiveData<StatusRequest>(StatusRequest.Loading)
    val movie: MutableLiveData<StatusRequest>
        get() = _movie
    private var _listMovie = MutableLiveData<MovieResponse>()
    val listMovie: MutableLiveData<MovieResponse>
        get() = _listMovie

    init {
        getStatusResponse()
        getListMovieResponse()
    }

    fun getStatusResponse() {
        _movie.postValue(StatusRequest.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getRandomMovie()
            } catch (e: Exception) {
                _movie.postValue(StatusRequest.Error(e.checkingResponse()))
                return@launch
            }
            response.body()?.let {
                _movie.postValue(StatusRequest.Success(it))
            } ?: _movie.postValue(StatusRequest.Error(NullPointerException().checkingResponse()))
        }
    }

    private fun getListMovieResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getListMovie()
            } catch (e: Exception) {
                e.checkingResponse()
                return@launch
            }
            _listMovie.postValue(response.body())
            // Здесь должна быть проверка на null
        }
    }


    override fun onCleared() {
        super.onCleared()
        jobResponseMovie = null
        jobRepeatRequest = null
    }
}