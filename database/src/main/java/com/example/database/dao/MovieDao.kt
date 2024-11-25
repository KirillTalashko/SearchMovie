package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.modelEntity.MovieEntity

@Dao
interface MovieDao {
    @Insert(entity = MovieEntity::class, OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM RANDOM_MOVIE WHERE ID > :step LIMIT :limit")
    fun getListMovie(limit: Int, step: Int): List<MovieEntity>

    @Query("SELECT * FROM RANDOM_MOVIE ORDER BY RANDOM() % (SELECT COUNT(ID) FROM RANDOM_MOVIE)")
    fun getRandomMovie(): MovieEntity

    @Query("SELECT * FROM RANDOM_MOVIE WHERE GENRES IN (:genres)")
    fun getMoviesByGenre(genres: List<String>): List<MovieEntity>


    @Delete(entity = MovieEntity::class)
    fun deleteMovies(movie: MovieEntity)
}