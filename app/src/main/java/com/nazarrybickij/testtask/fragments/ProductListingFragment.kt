package com.nazarrybickij.testtask.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nazarrybickij.testtask.*
import com.nazarrybickij.testtask.adapters.ProductAdapter
import com.nazarrybickij.testtask.viewmodels.ProductListingViewModel


class ProductListingFragment: Fragment() {
    private val adapter = ProductAdapter()
    private lateinit var navController:NavController
    private lateinit var viewModel: ProductListingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProductListingViewModel::class.java)
        navController = findNavController()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility = LinearLayout.VISIBLE

        return inflater.inflate(R.layout.product_listing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recyclerView)
        setListProduct(productRecyclerView)
        view.findViewById<LinearLayout>(R.id.sort_by_layout).setOnClickListener {

        }

    }
    private fun setListProduct(productRecyclerView:RecyclerView){
        viewModel.getProducts().observe(viewLifecycleOwner,{
            adapter.listProducts = it as MutableList<ProductEntity>
            adapter.notifyDataSetChanged()
        })
        val callback = object : ProductAdapter.AdapterCallback{
            override fun onItemClick(productEntity: ProductEntity) {
                val bundle = Bundle()
                bundle.putParcelable("product",productEntity)
                navController.navigate(R.id.action_productListingFragment_to_productPageFragment,bundle)
            }

            override fun onFavClick() {

            }
        }
        val orientation = App.getResources.configuration.orientation
        var spanCount = 2
        when(orientation){
            Configuration.ORIENTATION_PORTRAIT -> spanCount = 2
            Configuration.ORIENTATION_LANDSCAPE -> spanCount = 3
        }
        productRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        adapter.callback = callback
        productRecyclerView.adapter = adapter
    }
}