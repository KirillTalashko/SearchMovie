package com.example.searchmovie.di.modules

import android.content.Context
import com.example.searchmovie.SearchMovieApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val myApplication : SearchMovieApp) {
    @Singleton
    @Provides
    fun provideContext(): Context {
        return myApplication.applicationContext
    }

    @Singleton
    @Provides
    fun provideSearchMovieApp(): SearchMovieApp {
        return myApplication
    }
}