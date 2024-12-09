package com.example.logic.useCase

import androidx.lifecycle.MutableLiveData
import com.example.common.extension.toStringForRequestRating
import com.example.common.utils.Const
import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.database.repository.MovieLocalRepository
import com.example.logic.extension.toListMovieLogic
import com.example.logic.extension.toListString
import com.example.logic.model.GenreLogic
import com.example.logic.model.MovieLogic
import com.example.logic.state.MovieCardMovieFragmentState
import com.example.network.domain.repository.MovieRepository
import javax.inject.Inject

class MovieCardUseCase @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val networkManager: NetworkManager,
    private val errorManager: ErrorManager
) {

    var isLoading = false

    var trackingReadMore = MutableLiveData(false)

    var stateMoviesByGenre = MutableLiveData<MovieCardMovieFragmentState>()

    private var page = 1


    suspend fun getMoviesByGenreNetwork(movies: MovieLogic) {
        try {
            if (!isLoading) {
                isLoading = true
                stateMoviesByGenre.postValue(MovieCardMovieFragmentState.LoadingMoviesRelated)
                val response = apiRepository.getListMovie(
                    limit = Const.LIMIT,
                    page = page,
                    genres = movies.genres.toListString { GenreLogic(it.name) },
                    rating = movies.rating.kp.toStringForRequestRating()
                )
                response.body()?.let {
                    val currentList = it.movie.orEmpty()
                    stateMoviesByGenre.postValue(
                        MovieCardMovieFragmentState.SuccessMoviesRelated(
                            currentList.toListMovieLogic()
                        )
                    )
                    page++
                } ?: run {
                    //TODO("Обработать ошибку!")
                }
            }
        } catch (networkException: Exception) {
            getMoviesByGenreLocal(movies)
        } finally {
            isLoading = false
        }
    }

    suspend fun getMoviesByGenreLocal(movies: MovieLogic) {
        try {
            if (!isLoading) {
                isLoading = true
                val localMovies =
                    localRepository.getMovieByGenre(movies.genres.toListString { GenreLogic(it.name) })
                stateMoviesByGenre.postValue(
                    MovieCardMovieFragmentState.SuccessMoviesRelated(
                        movies = localMovies.toListMovieLogic()
                    )
                )
            }
        } catch (localException: Exception) {
            localException.localizedMessage?.let { errorManager.postError(it) }
        } finally {
            isLoading = false
        }
    }
}