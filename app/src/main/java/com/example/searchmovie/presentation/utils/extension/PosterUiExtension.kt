package com.example.searchmovie.presentation.utils.extension

import com.example.logic.model.PosterLogic
import com.example.searchmovie.presentation.modelMovie.PosterUi

fun PosterLogic.toPosterUi(): PosterUi {
    return PosterUi(
        url = url
    )
}

fun PosterUi?.toPosterLogic(): PosterLogic {
    return PosterLogic(
        url = this?.url
    )
}