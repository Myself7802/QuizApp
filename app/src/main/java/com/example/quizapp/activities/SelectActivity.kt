package com.example.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.ActivitySelectBinding
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Constants.ADMIN_PASSWORD
import com.example.quizapp.helpers.PasswordDialog
import com.example.quizapp.models.Question
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding
    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.admin.setOnClickListener {
            startActivity(Intent(this, QuestionViewActivity::class.java))
//            showPasswordDialog()
        }

        binding.kids.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("type","kids")
            startActivity(intent)
        }

        binding.adult.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("type","adult")
            startActivity(intent)
        }
    }

    private fun showPasswordDialog() {
        val passwordDialog = PasswordDialog(this) { enteredPassword ->
            if (enteredPassword == ADMIN_PASSWORD) {
                // Password is correct, navigate to the second activity
                val intent = Intent(this, QuestionViewActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }
        passwordDialog.show()
    }
}