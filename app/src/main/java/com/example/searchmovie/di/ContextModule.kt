package com.example.searchmovie.di

import android.content.Context
import com.example.searchmovie.utils.SearchMovieApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val myApplication : SearchMovieApp) {
    @Singleton
    @Provides
    fun provideContext() : Context {
        return myApplication.applicationContext
    }
}