package com.example.tester

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tester.databinding.SignInPageBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: SignInPageBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        binding.buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.textLogin.text.toString().trim()
        val password = binding.textPassword.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.textLogin.error = "Username is required"
            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.textPassword.error = "Password is required."
            return
        }

        if (password.length < 6) {
            binding.textPassword.error = "Password must be at least 6 characters."
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
                    Log.d("RegisterActivity", "createUserWithEmail:success")
                    Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT).show()
                    // Redirect to the login activity
                    finish()
                } else {
                    // If sign up fails, display a message to the user.
                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
