package com.example.foodmama.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.foodmama.CategoryMealsActivity
import com.example.foodmama.MainActivity
import com.example.foodmama.R
import com.example.foodmama.adapter.CategoriesAdapter
import com.example.foodmama.models.Categories
import com.example.foodmama.viewModel.MainActivityViewModel

class CategoryFragment : Fragment() {

    @BindView(R.id.rv_category_fragment)
    lateinit var rvCategoryFragment: RecyclerView

    private lateinit var thisView: View
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityViewModel = (activity as MainActivity).mainActivityViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        thisView = inflater.inflate(R.layout.fragment_category, container, false)
        ButterKnife.bind(this, thisView)

        prepareCategoryListRecyclerView()
        observeCategoryList()
        onCategoryClicked()
        return thisView
    }

    private fun onCategoryClicked() {
        categoriesAdapter.onCategoryClicked = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategoryList() {
        mainActivityViewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setData(it as ArrayList<Categories>)
        })
    }

    private fun prepareCategoryListRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        rvCategoryFragment.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }


}