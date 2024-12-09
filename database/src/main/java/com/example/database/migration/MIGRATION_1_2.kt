package com.example.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MIGRATION_1_2 : Migration(1, 2) {
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