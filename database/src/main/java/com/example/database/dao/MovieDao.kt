package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.modelEntity.MovieEntity

@Dao
interface MovieDao {
    @Insert(entity = MovieEntity::class, OnConflictStrategy.NONE)
    fun insertNewRandomMovie(movie: MovieEntity)

    @Query("SELECT * FROM RANDOM_MOVIE WHERE id LIKE :id")
    fun getMovieById(id:Long) : MovieEntity

    @Delete(entity = MovieEntity::class)
    fun deleteMovies(movie: MovieEntity)
}