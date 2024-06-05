package com.example.tester

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tester.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        // Log out button click listener
        binding.buttonLogout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Quiz button click listener
        binding.buttonQuiz.setOnClickListener {
            startActivity(Intent(this, QuizMainActivity::class.java))
            finish()
        }

        // Chatbot button click listener
        binding.buttonChatbot.setOnClickListener {
            // Add code to navigate to ChatbotActivity
            Toast.makeText(this, "Chatbot is under construction", Toast.LENGTH_SHORT).show()
        }
    }

}
