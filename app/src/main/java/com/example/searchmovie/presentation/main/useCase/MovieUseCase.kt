package com.example.searchmovie.presentation.main.useCase

import com.example.common.extension.isCheckErrorNetwork
import com.example.common.extension.log
import com.example.common.model.DialogParameterMode
import com.example.common.utils.Const
import com.example.common.utils.DisplayMode
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.toListMovieUi
import com.example.searchmovie.core.extension.toMovieEntity
import com.example.searchmovie.core.extension.toMovieUi
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.NetworkManager
import com.example.searchmovie.presentation.main.state.MovieMainFragmentState
import com.example.searchmovie.presentation.main.state.MoviesMainFragmentState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val networkManager: NetworkManager,
    private val errorManager: ErrorManager
) {

    val stateRandomMovie =
        MutableStateFlow<MovieMainFragmentState>(MovieMainFragmentState.LoadingMovie)
    val stateListMovie =
        MutableStateFlow<MoviesMainFragmentState>(MoviesMainFragmentState.LoadingListMovie)

    var isLoading = false
    private var page = 1
    private var step = 0

    suspend fun getMovie() {
        " movie icConnect ${networkManager.isConnect()}".log()
        if (networkManager.isConnect()) {
            getMovieNetwork()
        } else {
            getMovieLocal()
        }
    }

    suspend fun getMovies() {
        " movies icConnect ${networkManager.isConnect()}".log()
        if (networkManager.isConnect()) {
            getMoviesNetwork()
        } else {
            getMoviesLocal()
        }
    }

    private suspend fun getMovieNetwork() {
        try {
            "Зашел в метод getMovieNetwork".log()
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val repository = apiRepository.getRandomMovie()
            repository.body()?.let { movie ->
                stateRandomMovie.emit(
                    MovieMainFragmentState.SuccessMovie(
                        movie = movie.toMovieUi(),
                        isLocalDate = false
                    )
                )
                saveMovieInDatabase(movie)
            } ?: run {
                //TODO("Обработать ошибку")
            }
        } catch (networkException: Exception) {
            "NETWORK MOVIE ${networkException.message}".log()
            networkException.isCheckErrorNetwork {
                errorManager.showDialogGetLocalData(
                    dialogParameterMode = DialogParameterMode(
                        isEnabled = true,
                        displayMode = DisplayMode.MOVIE
                    )
                )
            }
        }
    }

    suspend fun getMovieLocal() {
        try {
            "Зашел в метод getMovieLocal".log()
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val movie = localRepository.getRandomMovie()
            stateRandomMovie.emit(
                MovieMainFragmentState.SuccessMovie(
                    movie = movie.toMovieUi(),
                    isLocalDate = true
                )
            )
        } catch (localException: Exception) {
            "DATABASE MOVIE ${localException.message}".log()
            localException.localizedMessage?.let { errorManager.postError(it) }
            stateRandomMovie.emit(MovieMainFragmentState.Error)
        }
    }

    private suspend fun getMoviesNetwork() {
        try {
            "Зашел в метод getMoviesNetwork".log()
            if (!isLoading) {
                isLoading = true
                stateListMovie.emit(MoviesMainFragmentState.LoadingListMovie)
                val response = apiRepository.getListMovie(
                    limit = Const.LIMIT,
                    page = page,
                    rating = "5-10",
                    genres = emptyList()
                )
                response.body()?.let { movies ->
                    val currentList = movies.movie.orEmpty()
                    stateListMovie.emit(
                        MoviesMainFragmentState.SuccessListMovie(
                            listMovie = currentList.toListMovieUi(),
                            isLocalData = false
                        )
                    )
                    saveMovieInDatabase(currentList)
                    page++
                } ?: run {
                    //TODO("Обработать ошибку")
                }
            }
        } catch (networkException: Exception) {
            "NETWORK MOVIES ${networkException.message}".log()
            networkException.isCheckErrorNetwork {
                errorManager.showDialogGetLocalData(
                    dialogParameterMode = DialogParameterMode(
                        isEnabled = true,
                        displayMode = DisplayMode.MOVIES
                    )
                )
            }
        } finally {
            isLoading = false
        }
    }

    suspend fun getMoviesLocal() {
        try {
            "Зашел в метод getMoviesLocal".log()
            if (!isLoading) {
                isLoading = true
                val moviesFromDatabase = localRepository.getListMovie(
                    limit = Const.LIMIT,
                    step = step
                )
                stateListMovie.emit(
                    MoviesMainFragmentState.SuccessListMovie(
                        listMovie = moviesFromDatabase.toListMovieUi(),
                        isLocalData = true
                    )
                )
                step += Const.LIMIT
            }
        } catch (localException: Exception) {
            "DATABASE MOVIES ${localException.message}".log()
            localException.localizedMessage?.let { errorManager.postError(it) }
            stateRandomMovie.emit(MovieMainFragmentState.Error)
        } finally {
            isLoading = false
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
}