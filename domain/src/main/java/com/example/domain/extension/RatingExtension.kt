package com.example.domain.extension

import com.example.domain.model.RatingLogic
import com.example.network.modelsMovie.Rating


fun Rating.toRatingLogic(): RatingLogic {
    return RatingLogic(
        kp = this.kp,
        imd = this.imd
    )
}