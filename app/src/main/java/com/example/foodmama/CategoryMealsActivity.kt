package com.example.foodmama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.foodmama.adapter.CategoriesAdapter
import com.example.foodmama.adapter.CategoryMealsAdapter
import com.example.foodmama.fragments.HomeFragment
import com.example.foodmama.models.Categories
import com.example.foodmama.models.MealsByCategory
import com.example.foodmama.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    @BindView(R.id.tv_category_count)
    lateinit var tvCategoryCount: TextView

    @BindView(R.id.rv_meals)
    lateinit var rvMeals: RecyclerView

    private lateinit var categoryMealsViewModel: CategoryMealsViewModel
    private lateinit var categoryName: String
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_meals)
        ButterKnife.bind(this)

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        categoryMealsViewModel.getMealsByCategory(categoryName)
        prepareRecyclerView()
        observeCategoryMealsLiveData()
        onMealClick()
    }

    private fun onMealClick() {
        categoryMealsAdapter.onItemClick = {
            val intent = Intent(this, MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,it.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,it.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        rvMeals.apply {
            layoutManager = GridLayoutManager(this@CategoryMealsActivity,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }


    private fun observeCategoryMealsLiveData() {
        categoryMealsViewModel.observeMealsByCategoryListLiveData().observe(this, Observer {
            categoryMealsAdapter.setData(it as ArrayList<MealsByCategory>)
            tvCategoryCount.text = it.size.toString()
        })
    }
}