package com.example.searchmovie.presentation.cardMovie.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.extension.checkingResponse
import com.example.common.extension.convectInJsonForRequest
import com.example.common.utils.Const
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.extension.toListString
import com.example.searchmovie.core.model.MovieUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelCardMovie(private val repositoryImpl: MovieRepository) : ViewModel() {

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
            _stateListMovieByGenre.value = MovieCardMovieFragmentState.LoadingRelatedMovies
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
                            MovieCardMovieFragmentState.SuccessRelatedMovies(
                                currentList
                            )
                        )
                        page++
                    } ?: run {
                        _stateListMovieByGenre.postValue(
                            MovieCardMovieFragmentState.Error(
                                NullPointerException().checkingResponse()
                            )
                        )
                    }
                } catch (e: Exception) {
                    _stateListMovieByGenre.postValue(MovieCardMovieFragmentState.Error(e.checkingResponse()))
                } finally {
                    isLoading = false
                }
            }
        }
    }
}