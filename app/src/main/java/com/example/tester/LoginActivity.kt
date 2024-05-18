package com.example.tester

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tester.databinding.LoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: LoginPageBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("com.example.yourapp.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        // Check if user chose to stay signed in
        if (sharedPreferences.getBoolean("keepSignedIn", false)) {
            val email = sharedPreferences.getString("email", "")
            val password = sharedPreferences.getString("password", "")
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                loginUser(email, password)
            }
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.textLogin.text.toString().trim()
            val password = binding.textPassword.text.toString().trim()
            loginUser(email, password)
        }

        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email:String,password:String) {

        if (TextUtils.isEmpty(email)) {
            binding.textLogin.error = "Email is required."
            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.textPassword.error = "Password is required."
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("LoginActivity", "signInWithEmail:success")
                    Toast.makeText(this, "Welcome to FitQuiz", Toast.LENGTH_SHORT).show()

                    if (binding.checkBox.isChecked) {
                        sharedPreferences.edit().apply {
                            putBoolean("keepSignedIn", true)
                            putString("email", email)
                            putString("password", password)
                            apply()
                        }
                    } else {
                        sharedPreferences.edit().clear().apply()
                    }
                    // Redirect to the main activity
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
