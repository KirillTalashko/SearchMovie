package com.example.database.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.example.database.dao.MovieDao
import com.example.database.modelEntity.MovieEntity

class MovieLocalRepositoryImpl(private val movieDao: MovieDao) : MovieLocalRepository {

    override suspend fun getRandomMovie(): MovieEntity {
        return movieDao.getRandomMovie()
    }

    override suspend fun getMovies(lastDate: Long, limit: Int): List<MovieEntity> {
        return movieDao.getListMovie(lastDate, limit)
    }

    override suspend fun insertMovieIfNotExists(movieEntity: MovieEntity) {
        return movieDao.insertMovieIfNotExists(movieEntity)
    }

    override suspend fun getMovieByGenre(query: SupportSQLiteQuery): List<MovieEntity> {
        return movieDao.getMoviesByGenre(query)
    }


}