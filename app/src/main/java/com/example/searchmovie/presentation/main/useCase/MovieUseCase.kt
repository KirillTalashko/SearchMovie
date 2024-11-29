package com.example.searchmovie.presentation.main.useCase

import com.example.common.extension.isCheckErrorNetwork
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
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
    private var isFirstLaunch: Boolean = false
    private var page = 1
    private var step = 0

    suspend fun getMovie(firstLaunch: Boolean) {
        isFirstLaunch = firstLaunch
        if (networkManager.isConnect()) {
            getMovieNetwork()
        } else if (!firstLaunch) {
            getMovieLocal()
        } else {
            errorManager.showDialogGetLocalData(
                dialogParameterMode = DialogParameterMode(
                    isEnabled = true
                )
            )
        }
    }

    suspend fun getMovies(firstLaunch: Boolean) {
        isFirstLaunch = firstLaunch
        if (networkManager.isConnect()) {
            getMoviesNetwork()
        } else if (!firstLaunch) {
            getMoviesLocal()
        } else {
            errorManager.showDialogGetLocalData(
                dialogParameterMode = DialogParameterMode(
                    isEnabled = true
                )
            )
        }
    }

    private suspend fun getMovieNetwork() {
        try {
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
            if (isFirstLaunch) {
                networkException.localizedMessage?.let { errorManager.postError(it) }
                networkException.isCheckErrorNetwork {
                    errorManager.showDialogGetLocalData(
                        dialogParameterMode = DialogParameterMode(
                            isEnabled = true,
                            displayMode = DisplayMode.MOVIE
                        )
                    )
                }
            } else getMovieLocal()
        }
    }

    suspend fun getMovieLocal() {
        try {
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val movie = localRepository.getRandomMovie()
            stateRandomMovie.emit(
                MovieMainFragmentState.SuccessMovie(
                    movie = movie.toMovieUi(),
                    isLocalDate = true
                )
            )
        } catch (localException: Exception) {
            localException.localizedMessage?.let { errorManager.postError(it) }
            stateRandomMovie.emit(MovieMainFragmentState.Error)
        }
    }

    private suspend fun getMoviesNetwork() {
        try {
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
            if (isFirstLaunch) {
                networkException.localizedMessage?.let { errorManager.postError(it) }
                networkException.isCheckErrorNetwork {
                    errorManager.showDialogGetLocalData(
                        dialogParameterMode = DialogParameterMode(
                            isEnabled = true,
                            displayMode = DisplayMode.MOVIES
                        )
                    )
                }
            } else getMoviesLocal()
        } finally {
            isLoading = false
        }
    }

    suspend fun getMoviesLocal() {
        try {
            if (!isLoading) {
                isLoading = true
                val moviesFromDatabase = withContext(Dispatchers.IO) {
                    localRepository.getListMovie(
                        limit = Const.LIMIT,
                        step = step
                    )
                }
                stateListMovie.emit(
                    MoviesMainFragmentState.SuccessListMovie(
                        listMovie = moviesFromDatabase.toListMovieUi(),
                        isLocalData = true
                    )
                )
                step += Const.LIMIT
            }
        } catch (localException: Exception) {
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