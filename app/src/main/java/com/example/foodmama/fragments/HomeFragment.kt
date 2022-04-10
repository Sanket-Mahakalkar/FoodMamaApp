package com.example.foodmama.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.example.foodmama.CategoryMealsActivity
import com.example.foodmama.MainActivity
import com.example.foodmama.MealDetailsActivity
import com.example.foodmama.R
import com.example.foodmama.adapter.CategoriesAdapter
import com.example.foodmama.adapter.PopularMealsAdapter
import com.example.foodmama.models.Categories
import com.example.foodmama.models.MealsByCategory
import com.example.foodmama.models.Meals
import com.example.foodmama.viewModel.MainActivityViewModel


class HomeFragment : Fragment() {

    @BindView(R.id.img_random_meal)
    lateinit var imgRandomMeal: ImageView

    @BindView(R.id.rv_popular_meals)
    lateinit var rvPopularMeals: RecyclerView

    @BindView(R.id.rv_categories)
    lateinit var rvCategories: RecyclerView

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var thisView: View
    private lateinit var randomMeal: Meals
    private lateinit var popularMealsAdapter: PopularMealsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.foodmama.fragments.Meal_id"
        const val MEAL_NAME = "com.example.foodmama.fragments.Meal_name"
        const val MEAL_THUMB = "com.example.foodmama.fragments.Meal_thumb"
        const val CATEGORY_NAME = "com.example.foodmama.fragments.Category_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = (activity as MainActivity).mainActivityViewModel
        popularMealsAdapter = PopularMealsAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        thisView = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this,thisView)

        mainActivityViewModel.getRandomMeal()
        observeRandomMeal()


        mainActivityViewModel.getPopularMeals()
        preparePopularMealsRecyclerView()
        observePopularMeals()
        onPopularMealClicked()

        mainActivityViewModel.getCategories()
        prepareCategoryListRecyclerView()
        observeCategoriesList()
        onCategoryClicked()
        return thisView
    }

    private fun onCategoryClicked() {
        categoriesAdapter.onCategoryClicked = {
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun onPopularMealClicked() {
        popularMealsAdapter.onItemClicked = {
            val intent = Intent(activity,MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID,it.idMeal)
            intent.putExtra(MEAL_NAME,it.strMeal)
            intent.putExtra(MEAL_THUMB,it.strMealThumb)
            startActivity(intent)
        }
    }


    @OnClick(R.id.img_random_meal)
   fun onRandomMealClick(){
       val intent = Intent(activity,MealDetailsActivity::class.java)
        intent.putExtra(MEAL_ID,randomMeal.idMeal)
        intent.putExtra(MEAL_NAME,randomMeal.strMeal)
        intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
       startActivity(intent)
   }



   private fun observeRandomMeal(){
        mainActivityViewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer
            { t ->
            Glide.with(this@HomeFragment).load(t?.strMealThumb)
                .into(imgRandomMeal)

            this.randomMeal = t;
        })
    }


    private fun observePopularMeals(){
        mainActivityViewModel.observePopularMealsLiveData().observe(viewLifecycleOwner, Observer {
            popularMealsAdapter.setData(it as ArrayList<MealsByCategory>)
        })
    }

    private fun observeCategoriesList(){
        mainActivityViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setData(it as ArrayList<Categories>)
        })
    }

    private fun preparePopularMealsRecyclerView(){
            rvPopularMeals.apply {
                layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                adapter = popularMealsAdapter
            }
    }

    private fun prepareCategoryListRecyclerView(){
        rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }
}