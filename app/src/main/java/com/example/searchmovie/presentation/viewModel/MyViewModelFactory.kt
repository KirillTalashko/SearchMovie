package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchmovie.domain.MyRepository

class MyViewModelFactory(private val repository : MyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelRandomTrailer::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModelRandomTrailer(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}