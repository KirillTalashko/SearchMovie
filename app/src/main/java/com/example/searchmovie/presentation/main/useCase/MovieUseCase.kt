package com.example.searchmovie.presentation.main.useCase

import com.example.common.utils.Const
import com.example.common.utils.Core
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
    private var lastDate: Long = 0L

    suspend fun getMovie() {
        if (networkManager.isConnect() || Core.isChecked) {
            getMovieNetwork()
        } else {
            getMovieLocal()
        }
    }

    suspend fun getMovies() {
        if (networkManager.isConnect() || Core.isChecked) {
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
                stateRandomMovie.emit(
                    MovieMainFragmentState.SuccessMovie(
                        movieUi = movie.toMovieUi(),
                        isLocalDate = false
                    )
                )
                saveMovieInDatabase(movie)
            } ?: run {
                //TODO("Обработать ошибку")
            }
        } catch (networkException: Exception) {
            getMovieLocal()
        }
    }

    suspend fun getMovieLocal() {
        try {
            stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val movie = localRepository.getRandomMovie()
            stateRandomMovie.emit(
                MovieMainFragmentState.SuccessMovie(
                    movieUi = movie.toMovieUi(),
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
            getMoviesLocal()
        } finally {
            isLoading = false
        }
    }

    suspend fun getMoviesLocal() {
        try {
            if (!isLoading) {
                isLoading = true
                stateListMovie.emit(MoviesMainFragmentState.LoadingListMovie)
                val moviesFromDatabase = localRepository.getMovies(
                    lastDate = lastDate,
                    limit = Const.LIMIT
                )
                lastDate = moviesFromDatabase[moviesFromDatabase.size - 1].date
                stateListMovie.emit(
                    MoviesMainFragmentState.SuccessListMovie(
                        listMovie = moviesFromDatabase.toListMovieUi(),
                        isLocalData = true
                    )
                )
            }
        } catch (localException: Exception) {
            localException.localizedMessage?.let { errorManager.postError(it) }
            stateRandomMovie.emit(MovieMainFragmentState.Error)
        } finally {
            isLoading = false
        }
    }

    private suspend fun saveMovieInDatabase(movie: Movie) {
        localRepository.insertMovieIfNotExists(movie.toMovieEntity())
    }

    private suspend fun saveMovieInDatabase(movies: List<Movie>) {
        movies.forEach { movie ->
            localRepository.insertMovieIfNotExists(movie.toMovieEntity())
        }
    }
}