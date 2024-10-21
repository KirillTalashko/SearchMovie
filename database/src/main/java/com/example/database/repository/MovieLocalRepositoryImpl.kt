package com.example.database.repository

import com.example.database.bd.MovieDatabase
import com.example.database.modelEntity.MovieEntity

class MovieLocalRepositoryImpl(private val dataBase: MovieDatabase) : MovieLocalRepository {
    override suspend fun insertMovie(movieEntity: MovieEntity) {
        dataBase.getMovieDao().insertNewRandomMovie(movieEntity)
    }

}