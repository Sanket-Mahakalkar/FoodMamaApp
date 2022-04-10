package com.example.foodmama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.foodmama.R
import com.example.foodmama.models.Categories

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var categories = ArrayList<Categories>()

    lateinit var onCategoryClicked:  ((Categories) -> Unit)

    fun setData(categories: ArrayList<Categories>){
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category,parent,false)

        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.populateData(categories[position])
        holder.itemView.setOnClickListener{
            onCategoryClicked.invoke(categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.img_category)
        lateinit var imgCategory: ImageView

        @BindView(R.id.tv_category_name)
        lateinit var tvCategoryName: TextView

        init {
            ButterKnife.bind(this,itemView)
        }

        fun populateData(categories: Categories) {
            Glide.with(this.itemView).load(categories.strCategoryThumb).into(imgCategory)
            tvCategoryName.text = categories.strCategory
        }

    }

}