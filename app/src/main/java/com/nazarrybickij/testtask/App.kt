package com.nazarrybickij.testtask

import android.app.Application
import android.content.res.Resources
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        getResources = resources
        context = this
    }
    companion object {
        lateinit var context:App
        lateinit var getResources: Resources
    }
}