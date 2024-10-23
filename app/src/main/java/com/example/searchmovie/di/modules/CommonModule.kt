package com.example.searchmovie.di.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.common.utils.GetAttributes
import com.example.common.utils.GetAttributesImpl
import com.example.database.repository.MovieLocalRepository
import com.example.network.domain.repository.MovieRepository
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import com.example.searchmovie.presentation.home.viewModel.ViewModelRandomMovie
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CommonModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        repository: MovieRepository,
        localRepository: MovieLocalRepository,
        attributes: GetAttributes
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ViewModelRandomMovie::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ViewModelRandomMovie(repository, localRepository, attributes) as T
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
    @Singleton
    fun getAttrs(context: Context): GetAttributes {
        return GetAttributesImpl(context)
    }
}