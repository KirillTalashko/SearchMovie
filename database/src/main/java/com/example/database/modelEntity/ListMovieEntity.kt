package com.example.database.modelEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_movie")
data class ListMovieEntity(
    @PrimaryKey val page: Long,
    val listMovie: List<MovieEntity>,
    val limit: Long
)