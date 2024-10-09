package com.example.searchmovie

import android.app.Application
import com.example.searchmovie.di.AppComponent
import com.example.searchmovie.di.modules.ContextModule
import com.example.searchmovie.di.DaggerAppComponent

class SearchMovieApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}
