package com.nazarrybickij.testtask.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.nazarrybickij.testtask.onboarding.OnboardingActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedData:DataStore<Preferences>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        sharedData = createDataStore(name = "settings")
        isOnBoardingFinished()
    }

    private fun isOnBoardingFinished() {
        GlobalScope.launch {
            val isFinished = readOnboardingInData() ?: false
            openActivity(isFinished)
        }
    }
    private fun openActivity(isFinished: Boolean){
        if (isFinished) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()
    }

    private suspend fun readOnboardingInData(): Boolean? {
        val dataStoreKey = booleanPreferencesKey("isFinished")
        val preference = sharedData.data.first()
        return preference[dataStoreKey]
    }
}