package com.example.searchmovie.utils

import android.app.Application
import com.example.searchmovie.di.AppComponent

class SearchMovieApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent
        TODO("пропал класс DaggerAppComponent, как правильно сделать ContextModule")
    }
}