package com.example.foodmama.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodmama.models.Meals

@Database(entities = [Meals::class], version = 2)
abstract class MealDatabase: RoomDatabase() {

    abstract fun getMealDao(): MealDao

    companion object{
        @Volatile
        private var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, MealDatabase::class.java,"mealDb")
                    .fallbackToDestructiveMigration()
                    .build()

            }

            return INSTANCE as MealDatabase
        }
    }
}