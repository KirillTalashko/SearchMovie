package com.example.searchmovie.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.NetworkManager
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import com.example.searchmovie.presentation.home.useCase.MovieUseCase
import com.example.searchmovie.presentation.home.useCase.MovieUseCaseImpl
import com.example.searchmovie.presentation.home.viewModel.ViewModelRandomMovie
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
                if (modelClass.isAssignableFrom(ViewModelCardMovie::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ViewModelCardMovie(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    @Provides
    fun provideUseCase(
        repository: MovieRepository,
        localRepository: MovieLocalRepository,
        networkManager: NetworkManager
    ): MovieUseCase {
        return MovieUseCaseImpl(repository, localRepository, networkManager)
    }

    @Provides
    fun provideNetworkManager(
        context: Context,
    ): NetworkManager {
        return NetworkManager(context)
    }
}