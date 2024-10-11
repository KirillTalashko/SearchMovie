package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.database.modelEntity.ListMovieEntity
import com.example.database.modelEntity.MovieEntity

@Dao
interface ListMovieDao {

    @Insert(entity = ListMovieEntity::class)
    fun setNewListMovie(lisMovie:ListMovieEntity)

    @Query("SELECT * FROM LIST_MOVIE WHERE page LIKE :page")
    fun getListMovieByPage(page: Long): List<MovieEntity>

    @Delete(entity = ListMovieEntity::class)
    fun deleteListMovie(listMovie: ListMovieEntity)
}