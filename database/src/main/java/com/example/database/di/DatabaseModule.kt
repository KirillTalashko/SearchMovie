package com.example.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.bd.MovieDatabase
import com.example.database.dao.MovieDao
import com.example.database.repository.MovieLocalRepository
import com.example.database.repository.MovieLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDuo(database: MovieDatabase): MovieDao {
        return database.getMovieDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(movieDuo: MovieDao): MovieLocalRepository {
        return MovieLocalRepositoryImpl(movieDuo)
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(" DELETE FROM random_movie WHERE id NOT IN (SELECT MIN(id) FROM random_movie GROUP BY idMovieKp)")
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS random_movie_new" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "idMovieKp INTEGER NOT NULL," +
                        "name TEXT, url TEXT," +
                        "ratingIMDb REAL NOT NULL DEFAULT 0, " +
                        "ratingKp REAL NOT NULL DEFAULT 0, " +
                        "duration INTEGER NOT NULL DEFAULT 0, " +
                        "year INTEGER NOT NULL DEFAULT 0, " +
                        "genres TEXT, type INTEGER NOT NULL DEFAULT 0, " +
                        "description TEXT," +
                        "date INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()})"
            )
            db.execSQL(
                "INSERT INTO random_movie_new (id, idMovieKp, name, url, ratingIMDb, ratingKp, duration, year, genres, type, description) " +
                        "SELECT id, idMovieKp, name, url, ratingIMDb, ratingKp, duration, year, genres, type, description " +
                        "FROM random_movie"
            )
            db.execSQL("DROP TABLE random_movie")
            db.execSQL("ALTER TABLE random_movie_new RENAME TO random_movie")
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_idMovieKp ON random_movie (idMovieKp)")
        }
    }
}
