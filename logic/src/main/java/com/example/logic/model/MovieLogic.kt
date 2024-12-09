package com.example.logic.model

data class MovieLogic(
    val id: Long,
    val name: String?,
    val poster: PosterLogic?,
    val rating: RatingLogic,
    val duration: Int,
    val year: Int,
    val genres: List<GenreLogic>?,
    val type: Int,
    val description: String?,
    val data: Long?
)
