package com.nazarrybickij.testtask.auth

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.Email
import com.mobsandgeeks.saripaar.annotation.NotEmpty
import com.mobsandgeeks.saripaar.annotation.Password
import com.nazarrybickij.testtask.R


class SingUpActivity : AppCompatActivity(), Validator.ValidationListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var validator: Validator

    @NotEmpty
    @Email
    private lateinit var emailEditText: EditText
    @Password(min = 6)
    private lateinit var  passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        auth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        val currentUser = auth.currentUser
        validator = Validator(this)
        validator.setValidationListener(this)
        if (currentUser != null){
            setResult(Activity.RESULT_OK)
            finish()
        }
        findViewById<Button>(R.id.sing_up_button).setOnClickListener {
            validator.validate()
        }
    }

    override fun onValidationSucceeded() {
        singUp()
    }

    private fun singUp() {
         val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onValidationFailed(errors: List<ValidationError>) {
        for (error in errors) {
            val view: View = error.view
            val message = error.getCollatedErrorMessage(this)
            if (view is EditText) {
                (view as EditText).error = message
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}