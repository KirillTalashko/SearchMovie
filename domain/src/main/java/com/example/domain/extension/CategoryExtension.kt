package com.example.domain.extension

import com.example.domain.model.CategoryLogic
import com.example.network.modelsMovie.Category

fun Category.toCategoryLogic(): CategoryLogic {
    return CategoryLogic(
        name = this.name,
        slug = this.slug
    )
}

fun List<Category>?.toListCategoryLogic(): List<CategoryLogic> {
    val newListCategoryLogic = mutableListOf<CategoryLogic>()
    this?.forEach { category ->
        newListCategoryLogic.add(
            CategoryLogic(
                name = category.name,
                slug = category.slug
            )
        )
    }
    return newListCategoryLogic
}