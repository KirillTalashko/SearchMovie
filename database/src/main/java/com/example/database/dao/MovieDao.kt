package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.modelEntity.MovieEntity

@Dao
interface MovieDao {
    @Insert(entity = MovieEntity::class, OnConflictStrategy.IGNORE)
    fun insertMovie(movie: MovieEntity)

    @Transaction
    fun insertMovieIfNotExists(movie: MovieEntity) {
        val existingMovie = getMovieByIdMovieKp(movie.idMovieKp)
        if (existingMovie == null) {
            insertMovie(movie)
        }
    }

    @Query("SELECT * FROM RANDOM_MOVIE WHERE idMovieKp = :idMovieKp")
    fun getMovieByIdMovieKp(idMovieKp: Long): MovieEntity?

    @Query("SELECT * FROM RANDOM_MOVIE WHERE DATE > :lastDate ORDER BY DATE ASC LIMIT :limit")
    fun getListMovie(lastDate: Long, limit: Int): List<MovieEntity>

    @Query("SELECT * FROM RANDOM_MOVIE ORDER BY RANDOM() % (SELECT COUNT(ID) FROM RANDOM_MOVIE)")
    fun getRandomMovie(): MovieEntity

    @Query("SELECT * FROM RANDOM_MOVIE WHERE GENRES IN (:genres)")
    fun getMoviesByGenre(genres: List<String>): List<MovieEntity>


    @Delete(entity = MovieEntity::class)
    fun deleteMovies(movie: MovieEntity)
}