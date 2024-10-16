package com.example.database.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.dao.MovieDao
import com.example.database.modelEntity.MovieEntity


@Database(
    version = 1,
    entities = [
        MovieEntity::class,
    ]
)
abstract class MovieDatabase: RoomDatabase() {

        abstract fun getMovieDao(): MovieDao

        companion object{
            @Volatile
            private var instance : MovieDatabase? = null

            fun getDatabase(context: Context) : MovieDatabase{
                if (instance == null){
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
