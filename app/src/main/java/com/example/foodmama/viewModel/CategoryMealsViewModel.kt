package com.example.foodmama.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodmama.api.ApiService
import com.example.foodmama.api.RetrofitClient
import com.example.foodmama.models.MealsByCategory
import com.example.foodmama.models.MealsByCategoryList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryMealsViewModel: ViewModel() {
    private val api = RetrofitClient.getRetrofit().create(ApiService::class.java)
    private val mealsByCategoryListLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String){
        api.getMealsByCategory(categoryName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: DisposableObserver<MealsByCategoryList>(){
                override fun onNext(mealsByCategoryList: MealsByCategoryList?) {
                    mealsByCategoryList?.let {
                        mealsByCategoryListLiveData.value = mealsByCategoryList.mealsByCategory
                    }
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {
                    dispose()
                }

            })
    }

    fun observeMealsByCategoryListLiveData(): LiveData<List<MealsByCategory>>{
        return mealsByCategoryListLiveData
    }
}