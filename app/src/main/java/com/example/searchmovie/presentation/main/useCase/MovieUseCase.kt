package com.example.searchmovie.presentation.main.useCase

import com.example.common.extension.log
import com.example.common.utils.Const
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
    private var localData = false
    private var page = 1
    private var step = 0

    suspend fun getMovie() {
        " movie isConnect ${networkManager.isConnect()}".log()
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
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val repository = apiRepository.getRandomMovie()
            repository.body()?.let { movie ->
                stateRandomMovie.emit(MovieMainFragmentState.SuccessMovie(movie.toMovieUi()))
                errorManager.dataObserver.emit(ErrorManager.DataErrorOrigin.NETWORK)
                saveMovieInDatabase(movie)
            } ?: stateRandomMovie.emit(MovieMainFragmentState.Error("список пуст"))
        } catch (networkException: Exception) {
            getMovieLocal()
        }
    }

    private suspend fun getMovieLocal() {
        try {
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            errorManager.dataObserver.emit(ErrorManager.DataErrorOrigin.DATABASE)
            val movie = localRepository.getRandomMovie()
            stateRandomMovie.emit(
                MovieMainFragmentState.SuccessMovie(
                    movie.toMovieUi()
                )
            )
        } catch (localException: Exception) {
            localException.message?.log()
            stateRandomMovie.emit(MovieMainFragmentState.Error(localException.message!!))
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
                            isLoading = true
                        )
                    )
                    errorManager.dataObserver.emit(ErrorManager.DataErrorOrigin.NETWORK)
                    saveMovieInDatabase(currentList)
                    page++
                } ?: run {
                    stateListMovie.emit(MoviesMainFragmentState.Error("список пуст"))
                }
            }
        } catch (networkException: Exception) {
            getMoviesLocal()
        } finally {
            isLoading = false
        }
    }

    private suspend fun getMoviesLocal() {
        try {
            if (!isLoading) {
                errorManager.dataObserver.emit(ErrorManager.DataErrorOrigin.DATABASE)
                isLoading = true
                val moviesFromDatabase = localRepository.getListMovie(
                    limit = Const.LIMIT,
                    step = step
                )
                stateListMovie.emit(
                    MoviesMainFragmentState.SuccessListMovie(
                        listMovie = moviesFromDatabase.toListMovieUi(),
                        isLoading = false
                    )
                )
                step += Const.LIMIT
            }
        } catch (localException: Exception) {
            stateListMovie.emit(MoviesMainFragmentState.Error(localException.message!!))
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