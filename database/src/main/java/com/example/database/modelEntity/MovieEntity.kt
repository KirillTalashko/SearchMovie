package com.example.database.modelEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "random_movie")
data class MovieEntity(
     @PrimaryKey val id: Long,
     val name: String,
     val url: String,
     val ratingIMDb: Float,
     val duration:Short,
     val year: Short,
     val genres: String,
     val type: Byte,
     val description: String
     //val similarMovies: список похожих фильмов
)