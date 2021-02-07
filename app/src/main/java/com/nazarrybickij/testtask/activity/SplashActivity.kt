package com.nazarrybickij.testtask.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nazarrybickij.testtask.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        isOnBoardingFinished()
    }
    private fun isOnBoardingFinished(){
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val isFinished = sharedPref.getBoolean("Finished", false)
        if(isFinished){
            startActivity(Intent(this, MainActivity::class.java))
        }else{
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()
    }
}