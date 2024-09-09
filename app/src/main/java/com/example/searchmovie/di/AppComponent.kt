package com.example.searchmovie.di

import com.example.searchmovie.presentation.HomeFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: HomeFragment)
}