package com.example.domain.extension

import com.example.domain.model.PosterLogic
import com.example.network.modelsMovie.Poster

fun Poster.toPosterLogic(): PosterLogic {
    return PosterLogic(
        url = this.url
    )
}
