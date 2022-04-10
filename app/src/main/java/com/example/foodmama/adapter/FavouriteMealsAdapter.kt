package com.example.foodmama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.foodmama.R
import com.example.foodmama.models.Meals
import com.example.foodmama.models.MealsByCategory

class FavouriteMealsAdapter: RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealsViewHolder>(){

    lateinit var onItemClick: ((Meals) -> Unit)

    /**
     * we will use Diffutil for recycler view. It works asynchronously.
     * Earlier we used to use notifyDataSetChanged and with this we refresh the whole recycler view
     * But diffUtil only refresh that element which gets affected like delete a element then nothing
     * happens to rest of the elements of List. Animation is also taken care by diffUtil class
     */
    private val diffUtil = object : DiffUtil.ItemCallback<Meals>(){
        override fun areItemsTheSame(oldItem: Meals, newItem: Meals): Boolean {
            // this function will compare the primary key of old item and new item
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meals, newItem: Meals): Boolean {
            // this function will compare the whole object
            return oldItem == newItem
        }

    }

    // we will use this differ to set value in List or get a item from the List
    val differ = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMealsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meals, parent, false)
        return FavouriteMealsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteMealsViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.populateData(meal)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavouriteMealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.img_meal)
        lateinit var imgMeal: ImageView

        @BindView(R.id.tv_meal_name)
        lateinit var tvMealName: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun populateData(meal: Meals) {
            Glide.with(this.itemView).load(meal.strMealThumb).into(imgMeal)
            tvMealName.text = meal.strMeal
        }
    }
}