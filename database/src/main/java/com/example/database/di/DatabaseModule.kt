package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.bd.MovieDatabase
import com.example.database.dao.MovieDao
import com.example.database.migration.Migration_1_2_Impl
import com.example.database.repository.MovieLocalRepository
import com.example.database.repository.MovieLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        ).addMigrations(Migration_1_2_Impl).build()
    }

    @Provides
    @Singleton
    fun provideMovieDuo(database: MovieDatabase): MovieDao {
        return database.getMovieDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(movieDuo: MovieDao): MovieLocalRepository {
        return MovieLocalRepositoryImpl(movieDuo)
    }
}