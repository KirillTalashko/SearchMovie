package com.example.searchmovie.presentation.utils.extension

import com.example.domain.model.CategoryLogic
import com.example.searchmovie.presentation.modelMovie.CategoryUi


fun CategoryLogic.toCategoryUi(): CategoryUi {
    return CategoryUi(
        name = this.name,
        slug = this.slug
    )
}

fun List<CategoryLogic>?.toListCategoryUi(): List<CategoryUi> {
    val newListCategoryLogic = mutableListOf<CategoryUi>()
    this?.forEach { category ->
        newListCategoryLogic.add(
            CategoryUi(
                name = category.name,
                slug = category.slug
            )
        )
    }
    return newListCategoryLogic
}