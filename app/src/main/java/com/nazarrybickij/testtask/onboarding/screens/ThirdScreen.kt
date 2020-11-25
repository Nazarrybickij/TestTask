package com.nazarrybickij.testtask.onboarding.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.nazarrybickij.testtask.R
import com.nazarrybickij.testtask.auth.SingInActivity
import com.nazarrybickij.testtask.auth.SingUpActivity
import com.squareup.picasso.Picasso


class ThirdScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        val image = view.findViewById<ImageView>(R.id.imageView3)
        Picasso.with(context).load(R.drawable.thri).fit().centerCrop().into(image)
        view.findViewById<LinearLayout>(R.id.finish).setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_productListingFragment)
            onBoardingFinished()
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

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            findNavController().navigate(R.id.action_viewPagerFragment_to_productListingFragment)
            onBoardingFinished()
        }
        if(requestCode == RC_SIGN_UP && resultCode == Activity.RESULT_OK) {
            findNavController().navigate(R.id.action_viewPagerFragment_to_productListingFragment)
            onBoardingFinished()
        }
    }
    companion object{
        val RC_SIGN_IN = 121
        val RC_SIGN_UP = 122
    }
}