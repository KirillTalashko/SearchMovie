package com.example.searchmovie.presentation.home.useCase

import androidx.lifecycle.MutableLiveData
import com.example.common.extension.checkingError
import com.example.common.extension.checkingResponse
import com.example.common.extension.log
import com.example.common.utils.Const
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.NetworkManager
import com.example.searchmovie.core.extension.toListMovieUi
import com.example.searchmovie.core.extension.toMovieEntity
import com.example.searchmovie.core.extension.toMovieUi
import com.example.searchmovie.presentation.home.viewModel.MovieMainFragmentState
import com.example.searchmovie.presentation.home.viewModel.MoviesMainFragmentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val networkManager: NetworkManager
) : MovieUseCase {

    private var jobGetMovie: Job? = null
    private var jobGetMoves: Job? = null
    private val _stateRandomMovie = MutableLiveData<MovieMainFragmentState>()
    private val _stateListMovie = MutableLiveData<MoviesMainFragmentState>()
    private var isLoading = false
    private var page = 1
    private var step = 0
    override fun getMovie() {
        jobGetMovie?.cancel()
        _stateRandomMovie.value = (MovieMainFragmentState.LoadingMovie)
        jobGetMovie = CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            try {
                //TODO("не корректно работает NetworkManager")
                "пришло ${networkManager.isConnect()}".log()
                val response = apiRepository.getRandomMovie()
                response.body()?.let {
                    _stateRandomMovie.postValue(MovieMainFragmentState.SuccessMovie(it.toMovieUi()))
                    saveMovieInDatabase(it)
                } ?: run {
                    _stateRandomMovie.postValue(
                        MovieMainFragmentState.Error(
                            NullPointerException().checkingResponse()
                        )
                    )
                }
            } catch (e: Exception) {
                if (e.checkingError()) {
                    val movie = localRepository.getRandomMovie()
                    _stateRandomMovie.postValue(
                        MovieMainFragmentState.SuccessMovie(
                            movie.toMovieUi()
                        )
                    )
                } else {
                    _stateRandomMovie.postValue(MovieMainFragmentState.Error(e.checkingResponse()))
                }
            }
        }
    }

    override fun getListMovie() {
        jobGetMoves?.cancel()
        if (!isLoading) {
            isLoading = true
            _stateListMovie.value = MoviesMainFragmentState.LoadingListMovie
            jobGetMoves = CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
                try {
                    val response = apiRepository.getListMovie(
                        limit = Const.LIMIT,
                        page = page,
                        rating = "5-10",
                        genres = emptyList()
                    )
                    response.body()?.let { listMovie ->
                        val currentList = listMovie.movie.orEmpty()
                        _stateListMovie.postValue(
                            MoviesMainFragmentState.SuccessListMovie(
                                listMovie = currentList.toListMovieUi(),
                                isLoading = true
                            )
                        )
                        saveMovieInDatabase(currentList)
                        page++
                    } ?: run {
                        _stateListMovie.postValue(
                            MoviesMainFragmentState.Error(
                                NullPointerException().checkingResponse()
                            )
                        )
                    }
                } catch (e: Exception) {
                    if (e.checkingError()) {
                        val listMovieFromDatabase = localRepository.getListMovie(
                            Const.LIMIT,
                            step
                        )
                        _stateListMovie.postValue(
                            MoviesMainFragmentState.SuccessListMovie(
                                listMovie = listMovieFromDatabase.toListMovieUi(),
                                isLoading = false
                            )
                        )
                        step += Const.LIMIT
                    } else {
                        _stateListMovie.postValue(MoviesMainFragmentState.Error(e.checkingResponse()))
                    }
                } finally {
                    isLoading = false
                }
            }
        }
    }

    private suspend fun saveMovieInDatabase(movie: Movie) {
        localRepository.insertMovie(movie.toMovieEntity())
    }

    private suspend fun saveMovieInDatabase(movies: List<Movie>) {
        movies.forEach { movie ->
            localRepository.insertMovie(movie.toMovieEntity())
        }
    }

    override fun getStateResponseMovie(): MutableLiveData<MovieMainFragmentState> {
        return _stateRandomMovie
    }

    override fun getStateResponseListMovie(): MutableLiveData<MoviesMainFragmentState> {
        return _stateListMovie
    }

    override fun getTheListenerState(): Boolean {
        return isLoading
    }
}