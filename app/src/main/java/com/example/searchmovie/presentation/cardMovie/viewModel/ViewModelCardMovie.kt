package com.example.searchmovie.presentation.cardMovie.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extension.checkingResponse
import com.example.common.extension.convectInJsonForRequest
import com.example.common.utils.Const
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.mapperInListString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelCardMovie(private val repositoryImpl: MovieRepository) : ViewModel() {

    private var isLoading = false
    fun getIsLoading() = isLoading


    private var _trackingReadMore = MutableLiveData(false)

    val trackingReadMore: LiveData<Boolean>
        get() = _trackingReadMore

    private var _stateListMovieByGenre = MutableLiveData<CardMovieFragmentStateRelatedMovies>()

    val stateListMovieByGenre: LiveData<CardMovieFragmentStateRelatedMovies>
        get() = _stateListMovieByGenre

    fun onReadMoreClicked() {
        _trackingReadMore.value = true
    }

    fun onLessMoreClicked() {
        _trackingReadMore.value = false
    }

    private var page = 1

    fun getListMovieByGenre(movie: Movie) {
        if (!isLoading) {
            isLoading = true
            _stateListMovieByGenre.value = CardMovieFragmentStateRelatedMovies.LoadingRelatedMovies
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repositoryImpl.getListMovie(
                            limit = Const.LIMIT,
                            page = page,
                            genres = movie.genres.mapperInListString(),
                            rating = movie.rating.kp.convectInJsonForRequest()
                        )
                    }
                    response.body()?.let {
                        val currentList = it.movie.orEmpty()
                        _stateListMovieByGenre.postValue(
                            CardMovieFragmentStateRelatedMovies.SuccessRelatedMovies(
                                currentList
                            )
                        )
                        page++
                    } ?: run {
                        _stateListMovieByGenre.postValue(
                            CardMovieFragmentStateRelatedMovies.Error(
                                NullPointerException().checkingResponse()
                            )
                        )
                    }
                } catch (e: Exception) {
                    _stateListMovieByGenre.postValue(CardMovieFragmentStateRelatedMovies.Error(e.checkingResponse()))
                } finally {
                    isLoading = false
                }
            }
        }
    }
}