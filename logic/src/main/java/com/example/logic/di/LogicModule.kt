package com.example.logic.di


import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.database.repository.MovieLocalRepository
import com.example.logic.useCase.MovieCardUseCase
import com.example.logic.useCase.MovieUseCase
import com.example.network.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides


@Module
class LogicModule {

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
            errorManager = errorManager
        )
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