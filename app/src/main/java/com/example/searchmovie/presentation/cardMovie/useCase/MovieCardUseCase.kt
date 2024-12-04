package com.example.searchmovie.presentation.cardMovie.useCase

import androidx.lifecycle.MutableLiveData
import com.example.common.extension.toStringForRequestRating
import com.example.common.utils.Const
import com.example.database.modelEntity.MovieEntity
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.network.modelsMovie.Genres
import com.example.searchmovie.core.extension.toListMovieUi
import com.example.searchmovie.core.extension.toListString
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.NetworkManager
import com.example.searchmovie.presentation.cardMovie.state.MovieCardMovieFragmentState
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


    suspend fun getMoviesByGenreNetwork(movies: MovieUi) {
        try {
            if (!isLoading) {
                isLoading = true
                stateMoviesByGenre.postValue(MovieCardMovieFragmentState.LoadingMoviesRelated)
                val response = apiRepository.getListMovie(
                    limit = Const.LIMIT,
                    page = page,
                    genres = movies.genres.toListString(),
                    rating = movies.rating.kp.toStringForRequestRating()
                )
                response.body()?.let {
                    val currentList = it.movie.orEmpty()
                    stateMoviesByGenre.postValue(
                        MovieCardMovieFragmentState.SuccessMoviesRelated(
                            currentList.toListMovieUi()
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

    suspend fun getMoviesByGenreLocal(movies: MovieUi) {
        try {
            if (!isLoading) {
                isLoading = true
                val localMovies = getMovieByGenres(movies.genres)
                stateMoviesByGenre.postValue(
                    MovieCardMovieFragmentState.SuccessMoviesRelated(
                        movies = localMovies.toListMovieUi()
                    )
                )
            }
        } catch (localException: Exception) {
            localException.localizedMessage?.let { errorManager.postError(it) }
        } finally {
            isLoading = false
        }
    }

    private suspend fun getMovieByGenres(listGenres: List<Genres>?): List<MovieEntity> {
        val query = listGenres.toListString()
        //return localRepository.getMovieByGenre(query.toSupportSQLiteQuery())
        return emptyList()
    }
}