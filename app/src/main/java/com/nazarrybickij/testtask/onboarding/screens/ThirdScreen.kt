package com.nazarrybickij.testtask.onboarding.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nazarrybickij.testtask.activity.MainActivity
import com.nazarrybickij.testtask.R
import com.nazarrybickij.testtask.auth.SingInActivity
import com.nazarrybickij.testtask.auth.SingUpActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ThirdScreen : Fragment() {
    private lateinit var sharedData: DataStore<Preferences>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)
        val image = view.findViewById<ImageView>(R.id.imageView3)
        sharedData = activity?.createDataStore("settings")!!
        Picasso.with(context).load(R.drawable.thri).fit().centerCrop().into(image)
        view.findViewById<LinearLayout>(R.id.finish).setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            saveOnboardingInData()
            startActivity(intent)
            requireActivity().finish()
        }
        view.findViewById<TextView>(R.id.sing_in).setOnClickListener {
            val intent = Intent(context,SingInActivity::class.java)
            startActivityForResult(intent,RC_SIGN_IN)
        }
        view.findViewById<TextView>(R.id.sing_up).setOnClickListener {
            val intent = Intent(context, SingUpActivity::class.java)
            startActivityForResult(intent, RC_SIGN_UP)
        }

        return view
    }


    private fun saveOnboardingInData() {
        lifecycleScope.launch {
            val dataStoreKey = booleanPreferencesKey("isFinished")
            sharedData.edit {
                it[dataStoreKey] = true
            }
        }
    }
    private fun startMainActivity(){
        val intent = Intent(context,MainActivity::class.java)
        saveOnboardingInData()
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            startMainActivity()
        }
        if(requestCode == RC_SIGN_UP && resultCode == Activity.RESULT_OK) {
            startMainActivity()
        }
    }
    companion object{
        val RC_SIGN_IN = 121
        val RC_SIGN_UP = 122
    }
}