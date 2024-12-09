package com.example.logic.extension

import com.example.logic.model.PosterLogic
import com.example.network.modelsMovie.Poster

fun Poster.toPosterLogic(): PosterLogic {
    return PosterLogic(
        url = this.url
    )
}
