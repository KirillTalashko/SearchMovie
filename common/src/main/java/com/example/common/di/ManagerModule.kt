package com.example.common.di

import android.content.Context
import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ManagerModule {

    @Provides
    @Singleton
    fun provideErrorManager(): ErrorManager {
        return ErrorManager()
    }

    @Provides
    @Singleton
    fun provideNetworkManager(
        context: Context,
    ): NetworkManager {
        return NetworkManager(context)
    }
}