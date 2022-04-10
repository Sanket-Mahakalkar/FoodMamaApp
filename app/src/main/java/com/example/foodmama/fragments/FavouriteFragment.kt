package com.example.foodmama.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.foodmama.MainActivity
import com.example.foodmama.MealDetailsActivity
import com.example.foodmama.R
import com.example.foodmama.adapter.FavouriteMealsAdapter
import com.example.foodmama.viewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteFragment : Fragment() {

    @BindView(R.id.rv_favourite_meals)
    lateinit var rvFavouriteMeals: RecyclerView
    
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var favouriteMealsAdapter: FavouriteMealsAdapter
    private lateinit var thisView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = (activity as MainActivity).mainActivityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        thisView = inflater.inflate(R.layout.fragment_favourite, container, false)
        ButterKnife.bind(this, thisView)

        mainActivityViewModel.getFavouriteMeals()
        prepareRecyclerView()
        observeFavMeals()
        onFavMealClick()
        deleteFavMeal()
        return thisView
    }

    private fun deleteFavMeal() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favouriteMealsAdapter.differ.currentList[position]
                mainActivityViewModel.deleteFavMealFromDb(meal)
                Snackbar.make(requireView(),"Meal deleted", Snackbar.LENGTH_LONG).setAction(
                    "UNDO",
                    View.OnClickListener {
                        mainActivityViewModel.insertFavMealIntoDb(meal)
                    }
                ).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvFavouriteMeals)
    }

    private fun onFavMealClick() {
       favouriteMealsAdapter.onItemClick = {
           val intent = Intent(activity, MealDetailsActivity::class.java)
           intent.putExtra(HomeFragment.MEAL_ID,it.idMeal)
           intent.putExtra(HomeFragment.MEAL_NAME,it.strMeal)
           intent.putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
           startActivity(intent)
       }
    }


    private fun observeFavMeals() {
        mainActivityViewModel.observeFavouriteMealsLiveData().observe(viewLifecycleOwner, Observer {
            favouriteMealsAdapter.differ.submitList(it)
        })
    }

    private fun prepareRecyclerView() {
        favouriteMealsAdapter = FavouriteMealsAdapter()
        rvFavouriteMeals.apply {
            layoutManager = GridLayoutManager(activity,2,  GridLayoutManager.VERTICAL, false)
            adapter = favouriteMealsAdapter
        }
    }
}