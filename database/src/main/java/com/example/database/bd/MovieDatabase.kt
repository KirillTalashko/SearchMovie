package com.example.database.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movieDao.bd"
                ).build()
            }
            return this.instance!!
        }
    }

}
