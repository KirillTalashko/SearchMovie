package com.example.database.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.ListMovieDao
import com.example.database.dao.MovieDao
import com.example.database.modelEntity.ListMovieEntity
import com.example.database.modelEntity.MovieEntity


@Database(
    version = 1,
    entities = [
        MovieEntity::class,
        ListMovieEntity::class
    ]
)
abstract class DatabaseManager: RoomDatabase() {

        abstract fun getMovieDao(): MovieDao
        abstract fun getListMovieDao(): ListMovieDao

}