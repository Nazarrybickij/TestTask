package com.nazarrybickij.testtask.network

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nazarrybickij.testtask.ProductEntity
import com.nazarrybickij.testtask.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class FirebaseRepo: IFireRepo {

    override fun getProduct(): Flow<Resource<List<ProductEntity>>> = callbackFlow {
        val document = Firebase.firestore.collection("dresses")
        val listener = document.addSnapshotListener { snapshot, exception ->
            if (snapshot != null) {
                offer(Resource.success(snapshot.toObjects(ProductEntity::class.java)))
            }
            exception?.let {
                offer(Resource.failed(it.message.toString()))
                cancel(it.message.toString())
            }
        }
        awaitClose {
            listener.remove()
            cancel()
        }
    }
}
