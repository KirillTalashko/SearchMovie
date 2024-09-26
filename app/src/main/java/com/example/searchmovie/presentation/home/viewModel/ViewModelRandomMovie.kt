package com.example.searchmovie.presentation.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.repositopy.MovieRepository
import com.example.searchmovie.core.extension.checkingResponse
import com.example.searchmovie.core.extension.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var isLoading = false
    fun getIsLoading() = isLoading

    private val _state = MutableLiveData<HomeFragmentState>()
    val state: LiveData<HomeFragmentState>
        get() = _state

    private var page = 1

    init {
        getRandomMovie()
        getListMovie()
    }

    fun getRandomMovie() {
        _state.postValue(HomeFragmentState.LoadingMovie)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomMovie()
                response.body()?.let {
                    _state.postValue(HomeFragmentState.SuccessMovie(it))
                }
                    ?: run { _state.postValue(HomeFragmentState.Error(NullPointerException().checkingResponse())) }
            } catch (e: Exception) {
                e.message?.log()
                _state.postValue(HomeFragmentState.Error(e.checkingResponse()))
            }
        }
    }

    fun getListMovie() {
        if (!isLoading) {
            isLoading = true
            _state.value = HomeFragmentState.LoadingListMovie
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repository.getListMovie(page = page)
                    }
                    response.body()?.let {
                        val currentList = it.movie.orEmpty()
                        _state.postValue(HomeFragmentState.SuccessListMovie(currentList))
                        page++
                    }
                        ?: run { _state.postValue(HomeFragmentState.Error(NullPointerException().checkingResponse())) }
                } catch (e: Exception) {
                    _state.postValue(HomeFragmentState.Error(e.checkingResponse()))

                } finally {
                    isLoading = false
                }
            }
        }
    }
}
