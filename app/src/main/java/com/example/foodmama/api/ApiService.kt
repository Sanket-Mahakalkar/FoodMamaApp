package com.example.foodmama.api

import com.example.foodmama.models.CategoryList
import com.example.foodmama.models.MealsByCategoryList
import com.example.foodmama.models.MealList
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    fun getRandomMeal(): Observable<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Observable<MealList>

    @GET("filter.php?")
    fun getPopularMeals(@Query("c") categoryName: String): Observable<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Observable<CategoryList>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") categoryName: String): Observable<MealsByCategoryList>
}