package com.example.foodmama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.foodmama.R
import com.example.foodmama.models.MealsByCategory

class PopularMealsAdapter: RecyclerView.Adapter<PopularMealsAdapter.PopularMealsViewHolder>() {

    private var popularMealsList = ArrayList<MealsByCategory>()

    lateinit var onItemClicked: ((MealsByCategory) -> Unit)

    fun setData(popularMealsByCategory: ArrayList<MealsByCategory>){
        this.popularMealsList = popularMealsByCategory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_meals,parent,false)

        return PopularMealsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMealsViewHolder, position: Int) {
        holder.populateData(popularMealsList[position])
        holder.itemView.setOnClickListener{
            onItemClicked.invoke(popularMealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return popularMealsList.size
    }

    class PopularMealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        @BindView(R.id.img_popular_meal_item)
        lateinit var imgPopularMealItem: ImageView

        init {
            ButterKnife.bind(this,itemView)
        }

        fun populateData(mealsByCategory: MealsByCategory) {
            Glide.with(this.itemView).load(mealsByCategory.strMealThumb).into(imgPopularMealItem)
        }

    }
}