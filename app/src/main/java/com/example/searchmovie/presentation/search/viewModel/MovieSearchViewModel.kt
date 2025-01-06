package com.example.searchmovie.presentation.search.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.state.MovieCategorySearchFragmentState
import com.example.domain.state.MoviesByCategoriesSearchFragmentState
import com.example.domain.useCase.MovieSearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieSearchViewModel(private val useCase: MovieSearchUseCase) : ViewModel() {

    fun getIsLoading() = useCase.isLoading

    val stateMovieCategory: LiveData<MovieCategorySearchFragmentState>
        get() = useCase.stateCategoryMovie.asLiveData()

    val stateMoviesByCategory: LiveData<MoviesByCategoriesSearchFragmentState>
        get() = useCase.stateMoviesByCategory.asLiveData()


    init {
        getCategory()
    }

    private fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getCategoryMovie()
        }
    }

    fun getMoviesByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getMoviesByCategory(category)
        }
    }


}