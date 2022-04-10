package com.example.foodmama

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.example.foodmama.api.Resource
import com.example.foodmama.db.MealDatabase
import com.example.foodmama.fragments.HomeFragment
import com.example.foodmama.models.Meals
import com.example.foodmama.models.MealsByCategory
import com.example.foodmama.viewModel.MealDetailsViewModel
import com.example.foodmama.viewModel.MealDetailsViewModelFactory
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MealDetailsActivity : AppCompatActivity() {

    @BindView(R.id.img_meal_details)
    lateinit var imgMeal: ImageView

    @BindView(R.id.collapsing_toolbar)
    lateinit var collapsingToolBar: CollapsingToolbarLayout

    @BindView(R.id.tv_instruction_details)
    lateinit var tvInstructionDetails: TextView

    @BindView(R.id.tv_category)
    lateinit var tvCategory: TextView

    @BindView(R.id.tv_area)
    lateinit var tvArea: TextView

    @BindView(R.id.btn_add_to_fav)
    lateinit var btnAddToFav: FloatingActionButton

    @BindView(R.id.tv_instruction)
    lateinit var tvInstruction: TextView

    @BindView(R.id.img_youtube)
    lateinit var imgYoutube: ImageView

    @BindView(R.id.progress_bar)
    lateinit var progressbar: ProgressBar


    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealDetailsViewModel: MealDetailsViewModel
    private lateinit var mealsByCategory: MealsByCategory
    private lateinit var youtubeLink: String
    private var meal: Meals? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)
        ButterKnife.bind(this)

        val mealDatabase = MealDatabase.getInstance(this)
        val factory = MealDetailsViewModelFactory(mealDatabase)
        mealDetailsViewModel = ViewModelProvider(this, factory)[MealDetailsViewModel::class.java]
        getInformationOfMeal()
        mealDetailsViewModel.getMealInstruction(mealId)
        renderView()
        observeMealDetailsLiveData()
    }


    private fun renderView() {
        Glide.with(applicationContext).load(mealThumb).into(imgMeal)
        collapsingToolBar.title = mealName
        collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getInformationOfMeal() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    @OnClick(R.id.img_youtube)
    fun onYoutubeClick(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        startActivity(intent)
    }

    @OnClick(R.id.btn_add_to_fav)
    fun onFavouriteButtonClick(){
        meal?.let {
            mealDetailsViewModel.insertFavMealIntoDb(it)
            Toast.makeText(this, "Meal saved..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeMealDetailsLiveData(){
        mealDetailsViewModel.observeMealDetailsLiveData().observe(this, Observer {

            when(it.status){

                Resource.Status.LOADING -> {
                    showProgressBar(true)
                }

                Resource.Status.SUCCESS -> {
                    showProgressBar(false)
                    meal = it.data
                    tvCategory.text = "Category: ${it?.data?.strCategory}"
                    tvArea.text = "Area: ${it?.data?.strArea}"
                    tvInstructionDetails.text = it?.data?.strInstructions
                    youtubeLink = it?.data?.strYoutube.toString()
                }

                Resource.Status.ERROR -> {
                    showProgressBar(true)
                    progressbar.visibility = View.GONE
                    Toast.makeText(this,it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showProgressBar(b: Boolean) {
        if(b){
            progressbar.visibility = View.VISIBLE
            tvArea.visibility = View.GONE
            tvCategory.visibility = View.GONE
            tvInstructionDetails.visibility = View.GONE
            imgYoutube.visibility = View.GONE
            btnAddToFav.visibility = View.GONE
            tvInstruction.visibility = View.GONE
        }
        else{
            progressbar.visibility = View.GONE
            tvArea.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvInstructionDetails.visibility = View.VISIBLE
            imgYoutube.visibility = View.VISIBLE
            btnAddToFav.visibility = View.VISIBLE
            tvInstruction.visibility = View.VISIBLE
        }

    }
}