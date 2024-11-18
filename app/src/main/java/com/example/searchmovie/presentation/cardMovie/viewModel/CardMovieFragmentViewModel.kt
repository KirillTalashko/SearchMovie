package com.example.searchmovie.presentation.cardMovie.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extension.convectInJsonForRequest
import com.example.common.extension.networkErrorHandler
import com.example.common.utils.Const
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.extension.toListString
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.presentation.cardMovie.state.MovieCardMovieFragmentState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CardMovieFragmentViewModel(private val repositoryImpl: MovieRepository) : ViewModel() {

    private var isLoading = false
    fun getIsLoading() = isLoading


    private var _trackingReadMore = MutableLiveData(false)

    val trackingReadMore: LiveData<Boolean>
        get() = _trackingReadMore

    private var _stateListMovieByGenre = MutableLiveData<MovieCardMovieFragmentState>()

    val stateListMovieByGenre: LiveData<MovieCardMovieFragmentState>
        get() = _stateListMovieByGenre

    fun onReadMoreClicked() {
        _trackingReadMore.value = true
    }

    fun onLessMoreClicked() {
        _trackingReadMore.value = false
    }

    private var page = 1

    fun getListMovieByGenre(movie: MovieUi) {
        if (!isLoading) {
            isLoading = true
            _stateListMovieByGenre.value = MovieCardMovieFragmentState.LoadingMoviesRelated
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repositoryImpl.getListMovie(
                            limit = Const.LIMIT,
                            page = page,
                            genres = movie.genres.toListString(),
                            rating = movie.rating.kp.convectInJsonForRequest()
                        )
                    }
                    response.body()?.let {
                        val currentList = it.movie.orEmpty()
                        _stateListMovieByGenre.postValue(
                            MovieCardMovieFragmentState.SuccessMoviesRelated(
                                currentList
                            )
                        )
                        page++
                    } ?: run {
                        _stateListMovieByGenre.postValue(
                            NullPointerException().networkErrorHandler()?.let {
                                MovieCardMovieFragmentState.Error(
                                    it
                                )
                            }
                        )
                    }
                } catch (e: Exception) {
                    _stateListMovieByGenre.postValue(e.networkErrorHandler()
                        ?.let { MovieCardMovieFragmentState.Error(it) })
                } finally {
                    isLoading = false
                }
            }
        }
    }
}