package com.nazarrybickij.testtask.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nazarrybickij.testtask.*
import com.nazarrybickij.testtask.adapters.ProductAdapter
import com.nazarrybickij.testtask.databinding.FragmentProductListingBinding
import com.nazarrybickij.testtask.utils.Resource
import com.nazarrybickij.testtask.viewmodels.ProductListingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


class ProductListingFragment : Fragment() {
    private val adapter = ProductAdapter()
    private lateinit var navController: NavController
    private val viewModel: ProductListingViewModel by viewModels()
    private lateinit var binding: FragmentProductListingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_listing, container, false)
        binding = FragmentProductListingBinding.bind(view)
        navController = findNavController()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.visibility =
            LinearLayout.VISIBLE

        return view
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recyclerView)
        setListProduct(productRecyclerView)
        startObserving()
        binding.sortByLayout.setOnClickListener {

        }

    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    private fun startObserving() {
        viewModel.getProducts().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    adapter.listProducts = it.data as MutableList<ProductEntity>
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = LinearLayout.GONE
                    binding.productRecyclerView.visibility = LinearLayout.VISIBLE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = LinearLayout.VISIBLE
                    binding.productRecyclerView.visibility = LinearLayout.GONE
                }
                is Resource.Failed -> {
                    Toast.makeText(activity, "I DO NOT KNOW", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = LinearLayout.GONE
                    binding.productRecyclerView.visibility = LinearLayout.GONE
                }
                else -> Unit
            }
        })
    }

    private fun setListProduct(productRecyclerView: RecyclerView) {
        val orientation = App.getResources.configuration.orientation
        var spanCount = 2
        val callback = object : ProductAdapter.AdapterCallback {
            override fun onItemClick(productEntity: ProductEntity) {
                val bundle = Bundle()
                bundle.putParcelable("product", productEntity)
                navController.navigate(
                    R.id.action_productListingFragment_to_productPageFragment,
                    bundle
                )
            }

            override fun onFavClick() {

            }
        }
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> spanCount = 2
            Configuration.ORIENTATION_LANDSCAPE -> spanCount = 3
        }
        productRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
        adapter.callback = callback
        productRecyclerView.adapter = adapter
    }
}
