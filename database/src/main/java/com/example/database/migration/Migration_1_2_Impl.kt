package com.example.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration_1_2_Impl : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `random_movie_new` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "`idMovieKp` INTEGER NOT NULL, " +
                    "`name` TEXT, `url` TEXT," +
                    "`ratingIMDb` REAL NOT NULL DEFAULT 0, " +
                    "`ratingKp` REAL NOT NULL DEFAULT 0, " +
                    "`duration` INTEGER NOT NULL DEFAULT 0, " +
                    "`year` INTEGER NOT NULL DEFAULT 0, " +
                    "`genres` TEXT, `type` INTEGER NOT NULL DEFAULT 0, " +
                    "`description` TEXT, " +
                    "`date` INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()})"
        )
        db.execSQL("DROP TABLE random_movie")
        db.execSQL("ALTER TABLE random_movie_new RENAME TO random_movie")
    }
}