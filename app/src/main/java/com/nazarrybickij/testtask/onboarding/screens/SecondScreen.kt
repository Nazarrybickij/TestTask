package com.nazarrybickij.testtask.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.nazarrybickij.testtask.R
import com.squareup.picasso.Picasso


class SecondScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_second_screen, container, false)


        val image = view.findViewById<ImageView>(R.id.imageView2)
        Picasso.with(context).load(R.drawable.second).fit().centerCrop().into(image)

        return view
    }

}