package com.example.logic.extension

import com.example.logic.model.RatingLogic
import com.example.network.modelsMovie.Rating


fun Rating.toRatingLogic(): RatingLogic {
    return RatingLogic(
        kp = this.kp,
        imd = this.imd
    )
}