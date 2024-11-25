package com.example.searchmovie.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.NetworkManager
import com.example.searchmovie.presentation.cardMovie.useCase.MovieCardUseCase
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
        movieUseCase: MovieUseCase,
        movieCardUseCase: MovieCardUseCase,
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
        return MovieUseCase(
            apiRepository = repository,
            localRepository = localRepository,
            networkManager = networkManager,
            errorManager = errorManager,
        )
    }

    @Provides
    fun provideNetworkManager(
        context: Context,
    ): NetworkManager {
        return NetworkManager(context)
    }

    @Provides
    @Singleton
    fun provideErrorManager(context: Context): ErrorManager {
        return ErrorManager(context)
    }

    @Provides
    fun provideUseCaseMovieCard(
        repository: MovieRepository,
        localRepository: MovieLocalRepository,
        networkManager: NetworkManager,
        errorManager: ErrorManager
    ): MovieCardUseCase {
        return MovieCardUseCase(
            apiRepository = repository,
            localRepository = localRepository,
            networkManager = networkManager,
            errorManager = errorManager
        )
    }
}