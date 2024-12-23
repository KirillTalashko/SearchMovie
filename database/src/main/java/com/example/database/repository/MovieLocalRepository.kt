package com.example.database.repository

import com.example.database.modelEntity.MovieEntity

interface MovieLocalRepository {

    suspend fun getRandomMovie(): MovieEntity

    suspend fun getMovies(lastDate: Long, limit: Int): List<MovieEntity>

    suspend fun insertMovieIfNotExists(movieEntity: MovieEntity)

    suspend fun getMovieByGenre(genres: List<String>): List<MovieEntity>
}