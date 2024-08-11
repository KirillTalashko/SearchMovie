package com.example.searchmovie.model

data class RandomTrailerResponse(val persons : List<Person>)

data class Person(
    val id: Long,
    val photo: String,
    val name: String? = null,
    val enName: String,
    val description: Any? = null,
    val profession: String,
    val enProfession: String
)
