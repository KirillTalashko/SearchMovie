package com.example.database.repository

import com.example.database.dao.MovieDao
import com.example.database.modelEntity.MovieEntity

class MovieLocalRepositoryImpl(private val movieDao: MovieDao) : MovieLocalRepository {
    override suspend fun insertMovie(movieEntity: MovieEntity) {
        movieDao.insertMovie(movieEntity)
    }

    override suspend fun getRandomMovie(): MovieEntity {
        return movieDao.getRandomMovie()
    }

    override suspend fun getListMovie(limit: Int, step: Int): List<MovieEntity> {
        return movieDao.getListMovie(limit, step)
    }

    override suspend fun getMovieByGenre(genre: List<String>): List<MovieEntity> {
        return movieDao.getMoviesByGenre(genre)
    }


}