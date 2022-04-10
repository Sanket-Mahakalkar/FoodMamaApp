package com.example.foodmama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.foodmama.R
import com.example.foodmama.models.MealsByCategory
import com.example.foodmama.models.MealsByCategoryList

class CategoryMealsAdapter: RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealList = ArrayList<MealsByCategory>()
    lateinit var onItemClick: ((MealsByCategory) -> Unit)

    fun setData(mealList: ArrayList<MealsByCategory>){
        this.mealList = mealList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meals,parent,false)

        return CategoryMealsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        holder.populateData(mealList[position])
        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class CategoryMealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.img_meal)
        lateinit var imgMeal: ImageView

        @BindView(R.id.tv_meal_name)
        lateinit var tvMealName: TextView

        init {
            ButterKnife.bind(this,itemView)
        }

        fun populateData(mealsByCategory: MealsByCategory) {
            Glide.with(this.itemView).load(mealsByCategory.strMealThumb).into(imgMeal)
            tvMealName.text = mealsByCategory.strMeal
        }
    }
}