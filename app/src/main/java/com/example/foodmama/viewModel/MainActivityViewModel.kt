package com.example.foodmama.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodmama.api.ApiService
import com.example.foodmama.api.RetrofitClient
import com.example.foodmama.db.MealDatabase
import com.example.foodmama.models.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val mealDatabase: MealDatabase): ViewModel() {
    private val api: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)
    private val mealListLiveData = MutableLiveData<Meals>()
    private val popularMealsLiveData = MutableLiveData<List<MealsByCategory>>()
    private val categoriesLiveData = MutableLiveData<List<Categories>>()
    private lateinit var favouriteMealLiveData: LiveData<List<Meals>>

    fun getRandomMeal(){
        api.getRandomMeal()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: DisposableObserver<MealList?>() {
                override fun onNext(t: MealList?) {
                    mealListLiveData.value = t?.meals?.get(0)
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {
                    dispose()
                }
            })
    }

    fun getPopularMeals(){
        api.getPopularMeals("Seafood")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: DisposableObserver<MealsByCategoryList?>(){
                override fun onNext(t: MealsByCategoryList?) {
                    popularMealsLiveData.value = t?.mealsByCategory
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {
                    dispose()
                }

            })
    }

    fun getCategories(){
        api.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: DisposableObserver<CategoryList>(){
                override fun onNext(categoryList: CategoryList?) {
                    categoryList?.let {
                        categoriesLiveData.value = categoryList.categories
                    }
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {
                    dispose()
                }

            })

    }

    fun insertFavMealIntoDb(meal: Meals){
        viewModelScope.launch {
            mealDatabase.getMealDao().insertMeal(meal)
        }
    }

    fun deleteFavMealFromDb(meal: Meals){
        viewModelScope.launch {
            mealDatabase.getMealDao().deleteMeal(meal)
        }
    }

    fun getFavouriteMeals(){
        favouriteMealLiveData = mealDatabase.getMealDao().getAllMeals()
    }

    fun observeRandomMealLiveData(): LiveData<Meals>{
        return mealListLiveData
    }

    fun observePopularMealsLiveData(): LiveData<List<MealsByCategory>>{
        return popularMealsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Categories>>{
        return categoriesLiveData
    }

    fun observeFavouriteMealsLiveData(): LiveData<List<Meals>>{
        return favouriteMealLiveData
    }
}