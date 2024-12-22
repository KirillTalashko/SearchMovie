package com.example.searchmovie.di


import com.example.common.di.ManagerModule
import com.example.database.di.DatabaseModule
import com.example.logic.di.LogicModule
import com.example.network.di.NetworkModule
import com.example.searchmovie.di.modules.CommonModule
import com.example.searchmovie.di.modules.ContextModule
import com.example.searchmovie.presentation.MainActivity
import com.example.searchmovie.presentation.cardMovie.CardMovieFragment
import com.example.searchmovie.presentation.main.fragment.MainFragment
import com.example.searchmovie.worker.NetworkCheckerWorker
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        CommonModule::class,
        ContextModule::class,
        LogicModule::class,
        ManagerModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: CardMovieFragment)
    fun inject(activity: MainActivity)
    fun inject(networkCheckerWorker: NetworkCheckerWorker)
}