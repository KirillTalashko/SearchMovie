package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.extension.checkingResponse
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


    fun getStatusResponse() {
        _movie.postValue(StatusRequest.Loading)
        jobResponseMovie = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomMovie()
                if (response.errorBody() == null) {
                    TODO()
                } else {
                    _movie.postValue(StatusRequest.Success(response.body()!!))
                }
            } catch (e: Exception) {
                _movie.postValue(StatusRequest.Error(e.checkingResponse()))
            }
        }
    }

    fun getListMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val responseListMovie = repository.getListMovie()
            "Ответ: ${responseListMovie.body()}"
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobResponseMovie = null
        jobRepeatRequest = null
    }
}