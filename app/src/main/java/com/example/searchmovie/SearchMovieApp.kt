package com.example.searchmovie

import android.app.Application
import com.example.database.bd.MovieDatabase
import com.example.searchmovie.di.AppComponent
import com.example.searchmovie.di.DaggerAppComponent
import com.example.searchmovie.di.modules.ContextModule

class SearchMovieApp : Application() {

    lateinit var appComponent: AppComponent

    val dataBase: MovieDatabase by lazy(LazyThreadSafetyMode.NONE) {
        MovieDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}
