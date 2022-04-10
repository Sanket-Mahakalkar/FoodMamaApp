package com.example.foodmama.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodmama.db.MealDatabase

class MealDetailsViewModelFactory(private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailsViewModel(mealDatabase) as T
    }

}