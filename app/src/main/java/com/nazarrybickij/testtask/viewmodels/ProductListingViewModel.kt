package com.nazarrybickij.testtask.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nazarrybickij.testtask.network.FirebaseRepo
import com.nazarrybickij.testtask.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

class ProductListingViewModel : ViewModel() {
    @ExperimentalCoroutinesApi
    val repo = FirebaseRepo()
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try{
            repo.getProduct().collect{
                emit(it)
            }
        }catch (e: Exception){
            emit(Resource.failed(e.message.toString()))
            Log.e("ERROR:", e.message.toString())
        }
    }
}