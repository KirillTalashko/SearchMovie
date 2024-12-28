package com.example.domain.useCase

import com.example.common.utils.Const
import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.database.repository.MovieLocalRepository
import com.example.domain.extension.toListCategoryLogic
import com.example.domain.extension.toListMovieLogic
import com.example.domain.state.MovieCategorySearchFragmentState
import com.example.domain.state.MoviesByCategoriesSearchFragmentState
import com.example.network.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MovieSearchUseCase @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val networkManager: NetworkManager,
    private val errorManager: ErrorManager
) {

    val stateCategoryMovie =
        MutableStateFlow<MovieCategorySearchFragmentState>(MovieCategorySearchFragmentState.LoadingMoviesSearch)
    val stateMoviesByCategory =
        MutableStateFlow<MoviesByCategoriesSearchFragmentState>(
            MoviesByCategoriesSearchFragmentState.LoadingMoviesSearch
        )

    var isLoading = false
    private var page = 1
    private var currentCategory: String? = null


    suspend fun getCategoryMovie() {
        try {
            stateCategoryMovie.emit(MovieCategorySearchFragmentState.LoadingMoviesSearch)
            val response = apiRepository.getFilteringOptions("type")
            response.body()?.let { categories ->
                stateCategoryMovie.emit(
                    MovieCategorySearchFragmentState.SuccessMoviesSearch(
                        categories.toListCategoryLogic()
                    )
                )
            }
        } catch (networkException: Exception) {
            stateCategoryMovie.emit(MovieCategorySearchFragmentState.Error)
            errorManager.postError("$networkException")
        }
    }

    suspend fun getMoviesByCategory(category: String) {
        try {
            if (category != currentCategory) {
                page = 1
            }
            if (!isLoading) {
                isLoading = true
                val response = apiRepository.getListMovie(
                    limit = Const.LIMIT,
                    page = page,
                    rating = "3-10",
                    genres = emptyList(),
                    type = category
                )
                response.body()?.let { movies ->
                    val currentList = movies.movie.orEmpty().toListMovieLogic()
                    stateMoviesByCategory.emit(
                        MoviesByCategoriesSearchFragmentState.SuccessMoviesSearch(
                            movies = currentList,
                            update = currentCategory != category
                        )
                    )
                    page++
                    currentCategory = category
                } ?: run {
                    stateMoviesByCategory.emit(MoviesByCategoriesSearchFragmentState.Error)
                    errorManager.postError("Список пуст!")
                }
            }
        } catch (networkException: Exception) {
            stateMoviesByCategory.emit(MoviesByCategoriesSearchFragmentState.Error)
            networkException.localizedMessage?.let { errorManager.postError(it) }
        } finally {
            isLoading = false
        }
    }
}
