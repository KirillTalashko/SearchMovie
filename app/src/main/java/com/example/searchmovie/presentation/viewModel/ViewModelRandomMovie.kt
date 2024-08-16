package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.extension.checkingResponse
import com.example.searchmovie.extension.log
import com.example.searchmovie.model.Movie
import com.example.searchmovie.model.StatusRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private val coroutineContext = (Dispatchers.IO) + Job()
    private var scopeImp: CoroutineScope? = CoroutineScope(coroutineContext)
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
                if (response.isSuccessful) {
                    if (response.body()?.name == null) {
                        repeatResponse(response)
                        "Repeat: ${repeatResponse(response)}".log()
                    } else {
                        _movie.postValue(StatusRequest.Success(response.body()!!))
                        "Успешно jobResponseMovie : ${_movie.value}".log()
                    }
                } else
                    throw NullPointerException()
            } catch (e: Exception) {
                "${_movie.value}".log()
                _movie.postValue(StatusRequest.Error(e.checkingResponse()))
            }
        }
    }

    private fun repeatResponse(response: Response<Movie>) {
        var count = 0
        jobRepeatRequest = viewModelScope.launch(Dispatchers.IO) {
            "Попытка: $count".log()
            if (count > 3) {
                jobRepeatRequest!!.cancel()
                "Остановка jobRepeatRequest".log()
            } else if (response.body()?.name == null) {
                count++
            } else _movie.postValue(StatusRequest.Success(response.body()!!))
            "Успешно jobRepeatRequest : ${_movie.value}".log()
        }
    }

    override fun onCleared() {
        super.onCleared()
        scopeImp = null
    }
}