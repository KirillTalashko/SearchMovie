package com.example.searchmovie.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.NetworkManager
import com.example.searchmovie.presentation.cardMovie.viewModel.CardMovieFragmentViewModel
import com.example.searchmovie.presentation.main.useCase.MovieUseCase
import com.example.searchmovie.presentation.main.viewModel.ViewModelRandomMovie
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CommonModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        useCase: MovieUseCase,
        repository: MovieRepository,
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ViewModelRandomMovie::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ViewModelRandomMovie(useCase) as T
                }
                if (modelClass.isAssignableFrom(CardMovieFragmentViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return CardMovieFragmentViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    @Provides
    fun provideUseCase(
        repository: MovieRepository,
        localRepository: MovieLocalRepository,
        networkManager: NetworkManager,
        errorManager: ErrorManager
    ): MovieUseCase {
        return MovieUseCase(repository, localRepository, networkManager, errorManager)
    }

    @Provides
    fun provideNetworkManager(
        context: Context,
    ): NetworkManager {
        return NetworkManager(context)
    }

    @Provides
    fun provideErrorManager(context: Context): ErrorManager {
        return ErrorManager(context)
    }
}