package com.example.foodmama.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodmama.api.ApiService
import com.example.foodmama.api.Resource
import com.example.foodmama.api.RetrofitClient
import com.example.foodmama.db.MealDatabase
import com.example.foodmama.models.MealList
import com.example.foodmama.models.Meals
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class MealDetailsViewModel(private val mealDatabase: MealDatabase): ViewModel() {
    private val api = RetrofitClient.getRetrofit().create(ApiService::class.java)
    private val mealDetailsLiveData = MutableLiveData<Resource<Meals>>()

    fun getMealInstruction(mealId: String){
        mealDetailsLiveData.value = Resource.loading()
        api.getMealDetails(mealId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: DisposableObserver<MealList?>(){
                override fun onNext(t: MealList?) {
                    if(t?.meals?.isNotEmpty() == true){
                        mealDetailsLiveData.value = Resource.success(t.meals[0])
                    }
                    else{
                        mealDetailsLiveData.value = Resource.error("Sorry, could not load the data")
                    }
                }

                override fun onError(e: Throwable) {
                    mealDetailsLiveData.value = Resource.error("Sorry, could not load the data")
                }

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


    fun observeMealDetailsLiveData(): LiveData<Resource<Meals>>{
        return mealDetailsLiveData
    }
}