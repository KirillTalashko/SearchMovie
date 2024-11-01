package com.example.searchmovie.presentation.home.useCase

import androidx.lifecycle.MutableLiveData
import com.example.common.extension.checkingError
import com.example.common.extension.checkingResponse
import com.example.common.extension.log
import com.example.common.utils.Const
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.mapperInListMovie
import com.example.searchmovie.core.extension.mapperInMovie
import com.example.searchmovie.core.extension.mapperInMovieEntity
import com.example.searchmovie.presentation.home.viewModel.MoviesMainFragmentState
import com.example.searchmovie.presentation.home.viewModel.StateRandomMovieMainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository
) : MovieUseCase {

    private var jobGetMovie: Job? = null
    private var jobGetMoves: Job? = null

    private val _stateRandomMovie = MutableLiveData<StateRandomMovieMainFragment>()
    private val _stateListMovie = MutableLiveData<MoviesMainFragmentState>()

    private var isLoading = false
    private var page = 1
    private var step = 0
    override fun getMovie() {
        jobGetMovie?.cancel()
        _stateRandomMovie.value = (StateRandomMovieMainFragment.LoadingMovie)
        jobGetMovie = CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            try {
                val response = apiRepository.getRandomMovie()
                response.body()?.let {
                    _stateRandomMovie.postValue(StateRandomMovieMainFragment.SuccessMovie(it))
                    "Movie c сервера".log()
                    //saveMovieInDatabase(it)
                    //TODO("база данныз закрыта")
                } ?: run {
                    _stateRandomMovie.postValue(
                        StateRandomMovieMainFragment.Error(
                            NullPointerException().checkingResponse()
                        )
                    )
                }
            } catch (e: Exception) {
                if (e.checkingError()) {
                    "Movie c бд".log()
                    val movie = localRepository.getRandomMovie()
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
                                listMovie = currentList,
                                isLoading = true
                            )
                        )
                        //saveMovieInDatabase(currentList)
                        //TODO("база данныз закрыта")
                        "Movies с сервера".log()
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
                        "Movies с бд".log()
                        val listMovieFromDatabase = localRepository.getListMovie(
                            Const.LIMIT,
                            step
                        )
                        _stateListMovie.postValue(
                            MoviesMainFragmentState.SuccessListMovie(
                                listMovie = listMovieFromDatabase.mapperInListMovie(),
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
        localRepository.insertMovie(movie.mapperInMovieEntity())
    }

    private suspend fun saveMovieInDatabase(movies: List<Movie>) {
        movies.forEach { movie ->
            localRepository.insertMovie(movie.mapperInMovieEntity())
        }
    }

    override fun getStateResponseMovie(): MutableLiveData<StateRandomMovieMainFragment> {
        return _stateRandomMovie
    }

    override fun getStateResponseListMovie(): MutableLiveData<MoviesMainFragmentState> {
        return _stateListMovie
    }

    override fun getTheListenerState(): Boolean {
        return isLoading
    }
}