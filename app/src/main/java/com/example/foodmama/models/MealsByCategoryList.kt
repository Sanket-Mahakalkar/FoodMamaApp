package com.example.foodmama.models

import com.google.gson.annotations.SerializedName

data class MealsByCategoryList(
    @SerializedName("meals")
    val mealsByCategory: List<MealsByCategory>
)

data class MealsByCategory(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)
