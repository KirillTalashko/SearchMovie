package com.example.searchmovie.presentation.main.useCase

import com.example.common.extension.log
import com.example.common.utils.Const
import com.example.common.utils.DataErrorOrigin
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.toListMovieUi
import com.example.searchmovie.core.extension.toMovieEntity
import com.example.searchmovie.core.extension.toMovieUi
import com.example.searchmovie.core.utils.ErrorDialog
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
    private val errorManager: ErrorManager,
    private val errorDialog: ErrorDialog
) {

    val stateRandomMovie =
        MutableStateFlow<MovieMainFragmentState>(MovieMainFragmentState.LoadingMovie)
    val stateListMovie =
        MutableStateFlow<MoviesMainFragmentState>(MoviesMainFragmentState.LoadingListMovie)

    var isLoading = false
    private var page = 1
    private var step = 0

    suspend fun getMovie() {
        " movie icConnect ${networkManager.isConnect()} и isLocalData ${errorDialog.getIsLocalData()}".log()
        if (networkManager.isConnect() && !errorDialog.getIsLocalData()) {
            getMovieNetwork()
        } else if (!networkManager.isConnect() && errorDialog.getIsLocalData()) {
            getMovieLocal()
        } else {
            stateRandomMovie.emit(MovieMainFragmentState.Error(DataErrorOrigin.NETWORK))
        }
    }

    suspend fun getMovies() {
        " movies icConnect ${networkManager.isConnect()} и isLocalData ${errorDialog.getIsLocalData()}".log()
        if (networkManager.isConnect() && !errorDialog.getIsLocalData()) {
            getMoviesNetwork()
        } else if (!networkManager.isConnect() && errorDialog.getIsLocalData()) {
            getMoviesLocal()
        } else {
            stateListMovie.emit(MoviesMainFragmentState.Error(DataErrorOrigin.NETWORK))
        }
    }

    private suspend fun getMovieNetwork() {
        try {
            "Зашел в метод getMovieNetwork".log()
            errorManager.postError(DataErrorOrigin.NETWORK)
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
                stateRandomMovie.emit(MovieMainFragmentState.Error(DataErrorOrigin.NETWORK))
                errorManager.postError(DataErrorOrigin.NETWORK)
            }
        } catch (networkException: Exception) {
            stateRandomMovie.emit(MovieMainFragmentState.Error(DataErrorOrigin.NETWORK))
            //errorManager.postError(networkException,DataErrorOrigin.NETWORK)
        }
    }

    private suspend fun getMovieLocal() {
        try {
            "Зашел в метод getMovieLocal".log()
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            errorManager.postError(DataErrorOrigin.DATABASE)
            val movie = localRepository.getRandomMovie()
            stateRandomMovie.emit(
                MovieMainFragmentState.SuccessMovie(
                    movie = movie.toMovieUi(),
                    isLocalDate = true
                )
            )
        } catch (localException: Exception) {
            "DATABASE MOVIE ${localException.message}".log()
            stateRandomMovie.emit(MovieMainFragmentState.Error(DataErrorOrigin.DATABASE))
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
                    stateListMovie.emit(MoviesMainFragmentState.Error(DataErrorOrigin.NETWORK))
                    errorManager.postError(DataErrorOrigin.NETWORK)
                }
            }
        } catch (networkException: Exception) {
            "NETWORK MOVIES ${networkException.message}".log()
            getMoviesLocal()
        } finally {
            isLoading = false
        }
    }

    private suspend fun getMoviesLocal() {
        try {
            "Зашел в метод getMoviesLocal".log()
            errorManager.postError(DataErrorOrigin.DATABASE)
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
            stateListMovie.emit(MoviesMainFragmentState.Error(DataErrorOrigin.DATABASE))
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