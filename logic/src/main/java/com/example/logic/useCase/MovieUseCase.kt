package com.example.logic.useCase


import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.common.utils.`object`.Const
import com.example.common.utils.`object`.Core
import com.example.database.repository.MovieLocalRepository
import com.example.logic.extension.toListMovieLogic
import com.example.logic.extension.toMovieEntity
import com.example.logic.extension.toMovieLogic
import com.example.logic.state.MovieMainFragmentState
import com.example.logic.state.MoviesMainFragmentState
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
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
                        movieUi = movie.toMovieLogic(),
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
                    movieUi = movie.toMovieLogic(),
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
                            listMovie = currentList.toListMovieLogic(),
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
                        listMovie = moviesFromDatabase.toListMovieLogic(),
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