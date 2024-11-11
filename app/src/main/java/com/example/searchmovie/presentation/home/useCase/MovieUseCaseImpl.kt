package com.example.searchmovie.presentation.home.useCase

import com.example.common.extension.log
import com.example.common.utils.Const
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.toListMovieUi
import com.example.searchmovie.core.extension.toMovieEntity
import com.example.searchmovie.core.extension.toMovieUi
import com.example.searchmovie.core.manager.NetworkManager
import com.example.searchmovie.presentation.home.state.MovieMainFragmentState
import com.example.searchmovie.presentation.home.state.MoviesMainFragmentState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val networkManager: NetworkManager
) : MovieUseCase {

    private val _stateRandomMovie =
        MutableStateFlow<MovieMainFragmentState>(MovieMainFragmentState.LoadingMovie)
    private val _stateListMovie =
        MutableStateFlow<MoviesMainFragmentState>(MoviesMainFragmentState.LoadingListMovie)

    private var isLoading = false
    private var page = 1
    private var step = 0

    override suspend fun getMovie() {
        " movie isConnect ${networkManager.isConnect()}".log()
        if (networkManager.isConnect()) {
            getMovieNetwork()
        } else {
            getMovieLocal()
        }
    }

    override suspend fun getMovies() {
        " movies icConnect ${networkManager.isConnect()}".log()
        if (networkManager.isConnect()) {
            getMoviesNetwork()
        } else {
            getMoviesLocal()
        }
    }

    private suspend fun getMovieNetwork() {
        try {
            "метод getMovieNetwork".log()
            _stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val repository = apiRepository.getRandomMovie()
            repository.body()?.let { movie ->
                _stateRandomMovie.emit(MovieMainFragmentState.SuccessMovie(movie.toMovieUi()))
                saveMovieInDatabase(movie)
            } ?: _stateRandomMovie.emit(MovieMainFragmentState.Error("список пуст"))
        } catch (networkException: Exception) {
            _stateRandomMovie.emit(MovieMainFragmentState.Error(networkException.message!!))
        }
    }

    private suspend fun getMovieLocal() {
        try {
            "метод getMovieLocal".log()
            _stateRandomMovie.emit(MovieMainFragmentState.LoadingMovie)
            val movie = localRepository.getRandomMovie()
            _stateRandomMovie.emit(
                MovieMainFragmentState.SuccessMovie(
                    movie.toMovieUi()
                )
            )
        } catch (localException: Exception) {
            _stateRandomMovie.emit(MovieMainFragmentState.Error(localException.message!!))
        }
    }

    private suspend fun getMoviesNetwork() {
        try {
            "метод getMoviesNetwork".log()
            if (!isLoading) {
                isLoading = true
                _stateListMovie.emit(MoviesMainFragmentState.LoadingListMovie)
                val response = apiRepository.getListMovie(
                    limit = Const.LIMIT,
                    page = page,
                    rating = "5-10",
                    genres = emptyList()
                )
                response.body()?.let { movies ->
                    val currentList = movies.movie.orEmpty()
                    _stateListMovie.emit(
                        MoviesMainFragmentState.SuccessListMovie(
                            listMovie = currentList.toListMovieUi(),
                            isLoading = true
                        )
                    )
                    saveMovieInDatabase(currentList)
                    page++
                } ?: run {
                    _stateListMovie.emit(MoviesMainFragmentState.Error("список пуст"))
                }
            }
        } catch (networkException: Exception) {
            _stateListMovie.emit(MoviesMainFragmentState.Error(networkException.message!!))
        } finally {
            isLoading = false
        }
    }

    private suspend fun getMoviesLocal() {
        try {
            "метод getMoviesLocal".log()
            if (!isLoading) {
                isLoading = true
                val moviesFromDatabase = localRepository.getListMovie(
                    limit = Const.LIMIT,
                    step = step
                )
                _stateListMovie.emit(
                    MoviesMainFragmentState.SuccessListMovie(
                        listMovie = moviesFromDatabase.toListMovieUi(),
                        isLoading = false
                    )
                )
                step += Const.LIMIT
            }
        } catch (localException: Exception) {
            _stateListMovie.emit(MoviesMainFragmentState.Error(localException.message!!))
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

    override fun getMovieState(): MutableStateFlow<MovieMainFragmentState> {
        return _stateRandomMovie
    }

    override fun getMoviesState(): MutableStateFlow<MoviesMainFragmentState> {
        return _stateListMovie
    }

    override fun getListenerLoadingMovie(): Boolean {
        return isLoading
    }
}