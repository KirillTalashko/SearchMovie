package com.example.database.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.dao.MovieDao
import com.example.database.modelEntity.ConvertersGenres
import com.example.database.modelEntity.MovieEntity


@Database(
    version = 1,
    entities = [
        MovieEntity::class,
    ]
)
@TypeConverters(ConvertersGenres::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movieDao.bd"
    }

}
