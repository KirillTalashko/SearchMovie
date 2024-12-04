package com.example.common.extension

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

fun String.log() {
    Log.i("youTag", this)
}
fun String.cardMovieLog() {
    Log.i("cardMovie", this)
}
fun List<String>.toSupportSQLiteQuery(): SupportSQLiteQuery {
    val queryBuilder = StringBuilder("SELECT * FROM RANDOM_MOVIE WHERE ")
    this.forEachIndexed { index, _ ->
        if (index > 0) queryBuilder.append(" OR ")
        queryBuilder.append("genres LIKE '%' || ? || '%'")
    }
    return SimpleSQLiteQuery(queryBuilder.toString(), this.toTypedArray())
}