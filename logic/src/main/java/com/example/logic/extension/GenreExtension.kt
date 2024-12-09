package com.example.logic.extension

import com.example.logic.model.GenreLogic

fun <T> List<T>?.toListGenreLogic(mapper: (T) -> GenreLogic): List<GenreLogic> {
    val newGenreList = mutableListOf<GenreLogic>()
    this?.forEach { any ->
        newGenreList.add(mapper(any))
    }
    return newGenreList
}

fun <T> List<T>?.toListString(mapper: (T) -> GenreLogic): List<String> {
    val newListString = mutableListOf<String>()
    this?.forEach { any ->
        mapper(any).name?.let { newListString.add(it) }
    }
    return newListString
}

