package com.example.searchmovie.presentation.utils

import com.example.searchmovie.presentation.modelMovie.MovieUi


interface OnClickGetModel {
    fun getMovieModel(movie: MovieUi)
    fun isLocalData(): Boolean?
}