package com.example.foodmama.models

data class CategoryList(
    var categories: List<Categories>
)

data class Categories(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)
