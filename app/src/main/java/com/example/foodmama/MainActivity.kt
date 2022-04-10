package com.example.foodmama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodmama.db.MealDatabase
import com.example.foodmama.viewModel.MainActivityViewModel
import com.example.foodmama.viewModel.MainActivityViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val mainActivityViewModel: MainActivityViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val mainActivityViewModelFactory = MainActivityViewModelFactory(mealDatabase)
        ViewModelProvider(this,mainActivityViewModelFactory)[MainActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this,R.id.nav_host_fragment_container)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}