package com.example.domain.state

import com.example.domain.model.MovieLogic

sealed class MoviesByCategoriesSearchFragmentState {

    data object Error : MoviesByCategoriesSearchFragmentState()
    data class SuccessMoviesSearch(val movies: List<MovieLogic>) :
        MoviesByCategoriesSearchFragmentState()

    data object LoadingMoviesSearch : MoviesByCategoriesSearchFragmentState()
}