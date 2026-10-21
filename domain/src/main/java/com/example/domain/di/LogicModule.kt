package com.example.domain.di


import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.database.repository.MovieLocalRepository
import com.example.domain.useCase.MovieCardUseCase
import com.example.domain.useCase.MovieMainUseCase
import com.example.domain.useCase.MovieSearchUseCase
import com.example.network.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides


@Module
class LogicModule {

    @Provides
    fun provideMovieMainUseCase(
        repository: MovieRepository,
        localRepository: MovieLocalRepository,
        networkManager: NetworkManager,
        errorManager: ErrorManager
    ): MovieMainUseCase {
        return MovieMainUseCase(
            apiRepository = repository,
            localRepository = localRepository,
            networkManager = networkManager,
            errorManager = errorManager
        )
    }

    @Provides
    fun provideMovieCardUseCase(
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

    @Provides
    fun provideMovieSearchUseCase(
        repository: MovieRepository,
        localRepository: MovieLocalRepository,
        networkManager: NetworkManager,
        errorManager: ErrorManager
    ): MovieSearchUseCase {
        return MovieSearchUseCase(
            apiRepository = repository,
            localRepository = localRepository,
            networkManager = networkManager,
            errorManager = errorManager
        )
    }
}