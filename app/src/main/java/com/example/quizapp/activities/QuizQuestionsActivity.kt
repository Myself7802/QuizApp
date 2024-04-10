package com.example.quizapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.helpers.Constants
import com.example.quizapp.models.Question
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mCurrentPosition: Int = 1
    private var mQuestionList: List<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private lateinit var viewModel: QuestionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        mQuestionList = viewModel.getQuestions()
        setContentView(binding.root)
//        mQuestionList = Constants.getQuestions()
        setQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question = mQuestionList!!.get(mCurrentPosition - 1)

        defaultOptionsView()
        if (mCurrentPosition == mQuestionList!!.size) {
            binding.btnSubmit.text = "Finish"
        } else {
            binding.btnSubmit.text = "Submit"
        }

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition" + "/" + binding.progressBar.max

        binding.tvProgress.text = question.question
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(binding.tvOptionThree, 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(binding.tvOptionFour, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            Toast.makeText(
                                this,
                                "You have successfully completed the Quiz", Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.correctOption != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctOption, R.drawable.correct_option_border_bg)
                    if (mCurrentPosition == mQuestionList!!.size) {
                        binding.btnSubmit.text = "Finish"
                    } else {
                        binding.btnSubmit.text = "Go to next question"
                    }
                    mSelectedOptionPosition = 0
                }

            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }
}