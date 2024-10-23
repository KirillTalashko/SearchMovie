package com.example.database.repository

import com.example.database.dao.MovieDao
import com.example.database.modelEntity.MovieEntity

class MovieLocalRepositoryImpl(private val movieDao: MovieDao) : MovieLocalRepository {
    override suspend fun insertMovie(movieEntity: MovieEntity) {
        movieDao.insertNewRandomMovie(movieEntity)
    }

}