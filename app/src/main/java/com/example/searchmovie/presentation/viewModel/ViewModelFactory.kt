package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchmovie.domain.MovieRepository

class ViewModelFactory(private val repository : MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelRandomMovie::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelRandomMovie(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}