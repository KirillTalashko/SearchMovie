package com.example.searchmovie.presentation.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.extension.checkingResponse
import com.example.searchmovie.core.extension.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var isLoading = false
    fun getIsLoading() = isLoading

    private val _stateRandomMovie = MutableLiveData<HomeFragmentStateRandomMovie>()
    val stateRandomMovie: LiveData<HomeFragmentStateRandomMovie>
        get() = _stateRandomMovie

    private val _stateListMovie = MutableLiveData<HomeFragmentStateListMovie>()
    val stateListMovie: LiveData<HomeFragmentStateListMovie>
        get() = _stateListMovie

    private var page = 1

    init {
        getRandomMovie()
        getListMovie()
    }

    fun getRandomMovie() {
        _stateRandomMovie.postValue(HomeFragmentStateRandomMovie.LoadingMovie)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRandomMovie()
                response.body()?.let {
                    _stateRandomMovie.postValue(HomeFragmentStateRandomMovie.SuccessMovie(it))
                }
                    ?: run { _stateRandomMovie.postValue(HomeFragmentStateRandomMovie.Error(NullPointerException().checkingResponse())) }
            } catch (e: Exception) {
                "${e.message} randomMovie"
                _stateRandomMovie.postValue(HomeFragmentStateRandomMovie.Error(e.checkingResponse()))
            }
        }
    }

    fun getListMovie() {
        if (!isLoading) {
            isLoading = true
            _stateListMovie.value = HomeFragmentStateListMovie.LoadingListMovie
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repository.getListMovie(page = page)
                    }
                    response.body()?.let {
                        val currentList = it.movie.orEmpty()
                        _stateListMovie.postValue(HomeFragmentStateListMovie.SuccessListMovie(currentList))
                        page++
                    }
                        ?: run { _stateRandomMovie.postValue(HomeFragmentStateRandomMovie.Error(NullPointerException().checkingResponse())) }
                } catch (e: Exception) {
                    "${e.message} listMovie".log()
                    _stateListMovie.postValue(HomeFragmentStateListMovie.Error(e.checkingResponse()))

                } finally {
                    isLoading = false
                }
            }
        }
    }
}
