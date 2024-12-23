package com.example.searchmovie.presentation.utils.extension

import com.example.logic.model.RatingLogic
import com.example.searchmovie.presentation.modelMovie.RatingUi

fun RatingLogic.toRatingUi(): RatingUi {
    return RatingUi(
        kp = this.kp,
        imd = this.imd
    )
}