package com.example.searchmovie.presentation.cardMovie.useCase

import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.core.utils.NetworkManager
import javax.inject.Inject

class MovieCardUseCase @Inject constructor(
    private val apiRepository: MovieRepository,
    private val localRepository: MovieLocalRepository,
    private val networkManager: NetworkManager,
) {


}