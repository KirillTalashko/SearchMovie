package com.example.database.modelEntity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "random_movie", indices = [Index(value = ["idMovieKp"], unique = true)])
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idMovieKp: Long,
    val name: String?,
    val url: String?,
    val ratingIMDb: Float = 0f,
    val ratingKp: Float = 0f,
    val duration: Int = 0,
    val year: Int = 0,
    val genres: List<String>?,
    val type: Int = 0,
    val description: String?,
    val date: Long = System.currentTimeMillis()
)

class ConvertersGenres {

    @TypeConverter
    fun fromGenreList(listGenre: List<String>): String {
        return listGenre.joinToString(separator = ",")
    }

    @TypeConverter
    fun toGenreList(genre: String): List<String> {
        return genre.split(",").map { it.trim() }
    }
}