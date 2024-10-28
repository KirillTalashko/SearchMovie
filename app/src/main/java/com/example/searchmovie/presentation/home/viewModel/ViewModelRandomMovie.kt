package com.example.searchmovie.presentation.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extension.checkingError
import com.example.common.extension.checkingResponse
import com.example.common.utils.SingletonValues
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.mapperInListMovie
import com.example.searchmovie.core.extension.mapperInMovie
import com.example.searchmovie.core.extension.mapperInMovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelRandomMovie(
    private val repository: MovieRepository,
    private val localRepository: MovieLocalRepository,
) : ViewModel() {

    private var isLoading = false
    fun getIsLoading() = isLoading

    private val _stateRandomMovie = MutableLiveData<StateRandomMovieMainFragment>()
    val stateRandomMovie: LiveData<StateRandomMovieMainFragment>
        get() = _stateRandomMovie

    private val _stateListMovie = MutableLiveData<StateListMovieMainFragment>()
    val stateListMovie: LiveData<StateListMovieMainFragment>
        get() = _stateListMovie

    private var page = 1
    private var step = 0

    init {
        getRandomMovie()
        getListMovie()
    }

    fun getRandomMovie() {
        _stateRandomMovie.postValue(StateRandomMovieMainFragment.LoadingMovie)
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getRandomMovie()
                }
                response.body()?.let {
                    _stateRandomMovie.postValue(StateRandomMovieMainFragment.SuccessMovie(it))
                    saveMovieInDatabase(it)
                } ?: run {
                    _stateRandomMovie.postValue(
                        StateRandomMovieMainFragment.Error(
                            NullPointerException().checkingResponse()
                        )
                    )
                }
            } catch (e: Exception) {
                if (e.checkingError()) {
                    _stateRandomMovie.postValue(StateRandomMovieMainFragment.Error("Данные взяты с базы данных"))
                    val movie = withContext(Dispatchers.IO) {
                        localRepository.getRandomMovie()
                    }
                    _stateRandomMovie.postValue(
                        StateRandomMovieMainFragment.SuccessMovie(
                            movie.mapperInMovie()
                        )
                    )
                } else {
                    _stateRandomMovie.postValue(StateRandomMovieMainFragment.Error(e.checkingResponse()))
                }
            }
        }
    }

    fun getListMovie() {
        if (!isLoading) {
            isLoading = true
            _stateListMovie.value = StateListMovieMainFragment.LoadingListMovie
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repository.getListMovie(page = page, "5-10", emptyList())
                    }
                    response.body()?.let { listMovie ->
                        val currentList = listMovie.movie.orEmpty()
                        saveMovieInDatabase(currentList)
                        _stateListMovie.postValue(
                            StateListMovieMainFragment.SuccessListMovie(
                                currentList
                            )
                        )
                        page++
                    } ?: run {
                        _stateListMovie.postValue(
                            StateListMovieMainFragment.Error(
                                NullPointerException().checkingResponse()
                            )
                        )
                    }
                } catch (e: Exception) {
                    if (e.checkingError()) {
                        _stateListMovie.postValue(StateListMovieMainFragment.Error("Данные взяты с базы данных"))
                        val listMovieFromDatabase = withContext(Dispatchers.IO) {
                            localRepository.getListMovie(
                                SingletonValues.LIMIT,
                                step
                            )
                        }
                        _stateListMovie.postValue(
                            StateListMovieMainFragment.SuccessListMovie(
                                listMovieFromDatabase.mapperInListMovie()
                            )
                        )
                        step += SingletonValues.LIMIT

                    } else {
                        _stateListMovie.postValue(StateListMovieMainFragment.Error(e.checkingResponse()))
                    }
                } finally {
                    isLoading = false
                }
            }
        }
    }

    private fun saveMovieInDatabase(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.insertMovie(movie.mapperInMovieEntity())
    }

    private fun saveMovieInDatabase(movies: List<Movie>) = viewModelScope.launch(Dispatchers.IO) {
        movies.forEach { movie ->
            localRepository.insertMovie(movie.mapperInMovieEntity())
        }
    }

}
