package com.example.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adapters.QuestionAdapter
import com.example.quizapp.databinding.ActivityQuestionViewBinding
import com.example.quizapp.helpers.OnChangeClickListener
import com.example.quizapp.models.Question
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel

class QuestionViewActivity : AppCompatActivity(),OnChangeClickListener {

    private lateinit var binding: ActivityQuestionViewBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var adapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionViewBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        refreshAdapter()
    }

    override fun onPause() {
        super.onPause()
        refreshAdapter()
    }

    private fun refreshAdapter() {
        val questionsList: List<Question> = viewModel.getQuestions()

        if (questionsList.isEmpty()){
            binding.noQuestionText.visibility = View.VISIBLE
            binding.recyclerview.visibility = View.GONE
        } else{
            binding.noQuestionText.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE

            binding.recyclerview.layoutManager = LinearLayoutManager(this)
            adapter = QuestionAdapter(questionsList.toMutableList(),this)
            binding.recyclerview.adapter = adapter

        }
    }

    override fun onDeleteClick(question: Question, pos: Int) {
        viewModel.deleteQuestion(question)
    }

    override fun onEditClick(question: Question, pos: Int) {
        val intent = Intent(this, AdminActivity::class.java)
        intent.putExtra("question",question)
        startActivity(intent)
    }
}