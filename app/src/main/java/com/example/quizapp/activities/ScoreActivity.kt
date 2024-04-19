package com.example.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Constants.CORRECT
import com.example.quizapp.helpers.Constants.NUMBER_OF_QUESTION

class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.score.text = "${CORRECT} / ${NUMBER_OF_QUESTION}"
        binding.btnStart.setOnClickListener {
            CORRECT = 0
            NUMBER_OF_QUESTION = 0
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        CORRECT = 0
        NUMBER_OF_QUESTION = 0
    }
}