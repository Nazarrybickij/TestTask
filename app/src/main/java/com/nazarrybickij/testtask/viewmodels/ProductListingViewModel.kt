package com.nazarrybickij.testtask.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.testtask.App
import com.nazarrybickij.testtask.ProductEntity

class ProductListingViewModel:ViewModel() {
    private val listProducts = MutableLiveData<List<ProductEntity>>()

    fun getProducts():MutableLiveData<List<ProductEntity>>{
        App.dbFirebase.collection("dresses")
            .get()
            .addOnSuccessListener { result ->
                val list = result.toObjects(ProductEntity::class.java)
                listProducts.value = list
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
        return listProducts
    }
}