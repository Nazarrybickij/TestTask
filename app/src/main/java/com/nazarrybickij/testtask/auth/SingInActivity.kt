package com.nazarrybickij.testtask.auth

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nazarrybickij.testtask.App
import com.nazarrybickij.testtask.R

class SingInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null){
            setResult(Activity.RESULT_OK)
            finish()
        }
        findViewById<Button>(R.id.sing_in_button).setOnClickListener {
            val passwordEditText = findViewById<EditText>(R.id.password_edit_text)
            val eMailEditText = findViewById<EditText>(R.id.email_edit_text)
            singIn(passwordEditText.text.toString(),eMailEditText.text.toString())
        }
    }

    private fun singIn(password: String,eMail: String) {
        auth.signInWithEmailAndPassword(eMail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }
}