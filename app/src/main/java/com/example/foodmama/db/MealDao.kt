package com.example.foodmama.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodmama.models.Meals

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meals)

    @Delete
    suspend fun deleteMeal(meal: Meals)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meals>>
}