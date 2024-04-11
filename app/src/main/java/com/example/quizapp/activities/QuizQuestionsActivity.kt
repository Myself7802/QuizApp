package com.example.quizapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.helpers.Constants
import com.example.quizapp.models.Question
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding
import com.example.quizapp.helpers.Constants.TIMER
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mCurrentPosition: Int = 1
    private var mQuestionList: List<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private lateinit var viewModel: QuestionViewModel
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis: Long = TIMER // 25 seconds


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        mQuestionList = viewModel.getQuestions()
        setContentView(binding.root)
//        mQuestionList = Constants.getQuestions()

        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                // Move to the next question
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                    val intent = if (mCurrentPosition <= mQuestionList!!.size) {
                        setQuestion()
                        null
                    } else {
                        Toast.makeText(
                            this@QuizQuestionsActivity,
                            "You have successfully completed the Quiz", Toast.LENGTH_SHORT
                        ).show()
                        Intent(this@QuizQuestionsActivity, MainActivity::class.java)
                    }

                    intent?.let { startActivity(it) }

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

        setQuestion()
        binding.progressBar.max = mQuestionList!!.size

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.aLayout.setOnClickListener(this)
        binding.bLayout.setOnClickListener(this)
        binding.cLayout.setOnClickListener(this)
        binding.dLayout.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)


    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question = mQuestionList!!.get(mCurrentPosition - 1)

        updateUI(question.type)

        defaultOptionsView()
        if (mCurrentPosition == mQuestionList!!.size) {
            binding.btnSubmit.text = "Finish"
        } else {
            binding.btnSubmit.text = "Submit"
        }

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition" + "/" + mQuestionList!!.size

        binding.tvQuestion.text = question.question
        binding.questionImageview.setImageBitmap(question.questionImage?.let {
            Constants.byteArrayToBitmap(
                it
            )
        })

        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        binding.option1Imageview.setImageBitmap(question.optionOneImage?.let {
            Constants.byteArrayToBitmap(
                it
            )
        })
        binding.option2Imageview.setImageBitmap(question.optionTwoImage?.let {
            Constants.byteArrayToBitmap(
                it
            )
        })

        binding.option3Imageview.setImageBitmap(question.optionThreeImage?.let {
            Constants.byteArrayToBitmap(
                it
            )
        })
        binding.option4Imageview.setImageBitmap(question.optionFourImage?.let {
            Constants.byteArrayToBitmap(
                it
            )
        })

        countDownTimer.cancel()
        timeLeftInMillis = 25000
        countDownTimer.start()

    }

    private fun updateUI(type: String) {
        if (type == Constants.TEXT){
            binding.tvQuestion.visibility = View.VISIBLE
            binding.cd0.visibility = View.GONE
            binding.questionImageview.visibility = View.GONE

            binding.tvOptionOne.visibility = View.VISIBLE
            binding.tvOptionTwo.visibility = View.VISIBLE
            binding.tvOptionThree.visibility = View.VISIBLE
            binding.tvOptionFour.visibility = View.VISIBLE

            binding.cd1.visibility = View.GONE
            binding.cd2.visibility = View.GONE
            binding.cd3.visibility = View.GONE
            binding.cd4.visibility = View.GONE

        } else if (type == Constants.IMAGE){
            binding.tvQuestion.visibility = View.GONE
            binding.cd0.visibility = View.VISIBLE
            binding.questionImageview.visibility = View.VISIBLE

            binding.tvOptionOne.visibility = View.GONE
            binding.tvOptionTwo.visibility = View.GONE
            binding.tvOptionThree.visibility = View.GONE
            binding.tvOptionFour.visibility = View.GONE

            binding.cd1.visibility = View.VISIBLE
            binding.cd2.visibility = View.VISIBLE
            binding.cd3.visibility = View.VISIBLE
            binding.cd4.visibility = View.VISIBLE
        } else{
            binding.tvQuestion.visibility = View.VISIBLE
            binding.cd0.visibility = View.VISIBLE
            binding.questionImageview.visibility = View.VISIBLE

            binding.tvOptionOne.visibility = View.VISIBLE
            binding.tvOptionTwo.visibility = View.VISIBLE
            binding.tvOptionThree.visibility = View.VISIBLE
            binding.tvOptionFour.visibility = View.VISIBLE

            binding.cd1.visibility = View.VISIBLE
            binding.cd2.visibility = View.VISIBLE
            binding.cd3.visibility = View.VISIBLE
            binding.cd4.visibility = View.VISIBLE
        }
    }

    private fun defaultOptionsView() {

        val options = ArrayList<LinearLayout>()
        options.add(0, binding.aLayout)
        options.add(1, binding.bLayout)
        options.add(2, binding.cLayout)
        options.add(3, binding.dLayout)

        for (option in options) {
//            option.setTextColor(Color.parseColor("#7A8089"))
//            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.aLayout -> {
                selectedOptionView(binding.aLayout, 1)
            }
            R.id.bLayout -> {
                selectedOptionView(binding.bLayout, 2)
            }
            R.id.cLayout -> {
                selectedOptionView(binding.cLayout, 3)
            }
            R.id.dLayout -> {
                selectedOptionView(binding.dLayout, 4)
            }
            R.id.btn_submit -> {
                countDownTimer.cancel()
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

    private fun selectedOptionView(layout: LinearLayout, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
//        tv.setTextColor(Color.parseColor("#363A43"))
//        tv.setTypeface(tv.typeface, Typeface.BOLD)
        layout.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private fun updateCountDownText() {
        val secondsLeft = timeLeftInMillis / 1000
        binding.tvTimer.text = "Time Left: $secondsLeft s"
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.aLayout.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                binding.bLayout.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                binding.cLayout.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                binding.dLayout.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }
}