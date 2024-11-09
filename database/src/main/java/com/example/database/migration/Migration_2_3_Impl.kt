package com.example.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration_2_3_Impl : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL(
//            "DELETE FROM random_movie" +
//                    "WHERE id NOT IN(SELECT MIN(id)" +
//                    "FROM random_movie GROUP BY idMovieKp)"
//        )
//        db.execSQL(
//            "CREATE TABLE IF NOT EXISTS `random_movie_new` " +
//                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                    " `idMovieKP` INTEGER NOT NULL, " +
//                    "`name` TEXT, `url` TEXT," +
//                    "`ratingIMDb` REAL NOT NULL DEFAULT 0, " +
//                    "`ratingKp` REAL NOT NULL DEFAULT 0, " +
//                    "`duration` INTEGER NOT NULL DEFAULT 0, " +
//                    "`year` INTEGER NOT NULL DEFAULT 0, " +
//                    "`genres` TEXT, `type` INTEGER NOT NULL DEFAULT 0, " +
//                    "`description` TEXT, " +
//                    "`date` LONG DEFAULT CURRENT_TIMESTAMP)"
//        )
//        db.execSQL(
//            "INSERT INTO random_movie_new" +
//                    "(idMovieKp, name, url, ratingIMDb, ratingKp, duration, year, genres, type, description, date)" +
//                    "SELECT idMovieKp, name, url, ratingIMDb, ratingKp, duration, year, genres, type, description, date FROM random_movie"
//        )
//        db.execSQL("DROP TABLE random_movie")
//        db.execSQL("ALTER TABLE random_movie_new RENAME TO random_movie")
    }

}