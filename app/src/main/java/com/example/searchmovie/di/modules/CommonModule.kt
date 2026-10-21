package com.example.searchmovie.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.MovieCardUseCase
import com.example.domain.useCase.MovieMainUseCase
import com.example.domain.useCase.MovieSearchUseCase
import com.example.searchmovie.presentation.cardMovie.viewModel.CardMovieFragmentViewModel
import com.example.searchmovie.presentation.main.viewModel.ViewModelRandomMovie
import com.example.searchmovie.presentation.search.viewModel.MovieSearchViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CommonModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        movieUseCase: MovieMainUseCase,
        movieCardUseCase: MovieCardUseCase,
        movieSearchUseCase: MovieSearchUseCase
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ViewModelRandomMovie::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ViewModelRandomMovie(movieUseCase) as T
                }
                if (modelClass.isAssignableFrom(CardMovieFragmentViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return CardMovieFragmentViewModel(movieCardUseCase) as T
                }
                if (modelClass.isAssignableFrom(MovieSearchViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MovieSearchViewModel(movieSearchUseCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}