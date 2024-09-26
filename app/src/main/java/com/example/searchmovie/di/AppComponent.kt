package com.example.searchmovie.di

import com.example.searchmovie.di.modules.CommonModule
import com.example.searchmovie.di.modules.ContextModule
import com.example.searchmovie.di.modules.NetworkModule
import com.example.searchmovie.presentation.home.fragment.HomeFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CommonModule::class,
        ContextModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: HomeFragment)
}