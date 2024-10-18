package com.example.database.repository

import com.example.database.modelEntity.MovieEntity

interface MovieLocalRepository {

    suspend fun insertMovie(movieEntity: MovieEntity)
    suspend fun insertListMovie(movieEntity: List<MovieEntity>)
}