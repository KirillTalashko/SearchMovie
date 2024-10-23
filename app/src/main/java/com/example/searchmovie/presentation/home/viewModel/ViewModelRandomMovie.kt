package com.example.searchmovie.presentation.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extension.checkingResponse
import com.example.common.extension.log
import com.example.common.utils.GetAttributes
import com.example.database.modelEntity.MovieEntity
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.R
import com.example.searchmovie.core.ConvectorListGenreToString.Companion.mapperGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelRandomMovie(
    private val repository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val attributes: GetAttributes
) : ViewModel() {

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
                    localRepository.insertMovie(mapperMovie(it))
                }
                    ?: run {
                        _stateRandomMovie.postValue(
                            HomeFragmentStateRandomMovie.Error(
                                NullPointerException().checkingResponse()
                            )
                        )
                    }
            } catch (e: Exception) {
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
                    response.body()?.let { listMovie ->
                        val currentList = listMovie.movie.orEmpty()
                        withContext(Dispatchers.IO) {
                            currentList.forEach { movie ->
                                localRepository.insertMovie(mapperMovie(movie))
                            }
                            return@withContext
                        }
                        _stateListMovie.postValue(
                            HomeFragmentStateListMovie.SuccessListMovie(
                                currentList
                            )
                        )
                        page++
                    }
                        ?: run {
                            _stateRandomMovie.postValue(
                                HomeFragmentStateRandomMovie.Error(
                                    NullPointerException().checkingResponse()
                                )
                            )
                        }
                } catch (e: Exception) {
                    e.message?.log()
                    _stateListMovie.postValue(HomeFragmentStateListMovie.Error(e.checkingResponse()))

                } finally {
                    isLoading = false
                }
            }
        }
    }

    private fun mapperMovie(movie: Movie): MovieEntity {
        return MovieEntity(
            id = 0,
            idMovieKp = movie.id,
            name = movie.name ?: attributes.getAttrs(R.string.no_name),
            url = movie.poster?.url,
            ratingIMDb = movie.rating.imd,
            ratingKp = movie.rating.kp,
            duration = movie.duration,
            year = movie.year,
            genres = mapperGenre(movie.genres),
            type = movie.type,
            description = movie.description ?: attributes.getAttrs(R.string.no_description)
        )
    }


}
