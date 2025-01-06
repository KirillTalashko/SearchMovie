package com.example.domain.state

import com.example.domain.model.CategoryLogic

sealed class MovieCategorySearchFragmentState {

    data object Error : MovieCategorySearchFragmentState()
    data class SuccessMoviesSearch(val categories: List<CategoryLogic>) :
        MovieCategorySearchFragmentState()

    data object LoadingMoviesSearch : MovieCategorySearchFragmentState()
}