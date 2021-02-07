package com.nazarrybickij.testtask.network

import com.nazarrybickij.testtask.ProductEntity
import com.nazarrybickij.testtask.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IRepo {
    suspend fun getProduct(): Flow<Resource<List<ProductEntity>>>
}