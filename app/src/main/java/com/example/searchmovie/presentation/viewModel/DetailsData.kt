package com.example.searchmovie.presentation.viewModel

import com.example.searchmovie.model.Movie

data class DetailsData(
    val listMovie: List<Movie?>,
    val movie: Movie?,
    val movieError: String? = null,
    val movieListError: String? = null
)
