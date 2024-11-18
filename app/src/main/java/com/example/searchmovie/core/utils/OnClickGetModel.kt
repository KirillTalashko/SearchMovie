package com.example.searchmovie.core.utils

import com.example.searchmovie.core.model.MovieUi


interface OnClickGetModel {
    fun getMovieModel(movie: MovieUi)
    fun isLocalData(): Boolean?
}