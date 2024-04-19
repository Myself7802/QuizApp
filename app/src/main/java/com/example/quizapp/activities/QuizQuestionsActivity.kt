package com.example.quizapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Constants.CORRECT
import com.example.quizapp.helpers.Constants.LANGUAGE
import com.example.quizapp.helpers.Constants.NUMBER_OF_QUESTION
import com.example.quizapp.helpers.Constants.TIMER
import com.example.quizapp.models.Question
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlin.random.Random


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private var mCurrentPosition: Int = 1
    private var mQuestionList: List<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private lateinit var viewModel: QuestionViewModel
    private lateinit var countDownTimer: CountDownTimer
//    private lateinit var music: MediaPlayer
    private var timeLeftInMillis: Long = TIMER // 25 seconds
    var correctAnswerIndex = -1
    var isNewQuestion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        val list = viewModel.getQuestions(LANGUAGE)
        val mutableQuestionList = list.toMutableList()
        mutableQuestionList.shuffle(Random)
        mQuestionList = mutableQuestionList.toList().take(25)
        NUMBER_OF_QUESTION = mQuestionList!!.size
        setContentView(binding.root)
//        mQuestionList = Constants.getQuestions()
//        music = MediaPlayer.create(this@QuizQuestionsActivity, R.raw.second_sound)

        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                // Move to the next question
//                answerView(correctAnswerIndex, R.drawable.correct_option_border_bg)

                mCurrentPosition++
                val intent = if (mCurrentPosition <= mQuestionList!!.size) {
                    Handler().postDelayed({ setQuestion() }, 100)
                    null
                } else {
                    Toast.makeText(
                        this@QuizQuestionsActivity,
                        "You have successfully completed the Quiz", Toast.LENGTH_SHORT
                    ).show()
                    Intent(this@QuizQuestionsActivity, ScoreActivity::class.java)
                }

                intent?.let {
                    startActivity(it)
                    finish()
                }

//                music.stop()

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

        val circularProgressBar = findViewById<CircularProgressBar>(R.id.circularProgressBar)
        circularProgressBar.apply {
            // Set Progress
            progress = 25f
            // or with animation
            setProgressWithAnimation(65f, 1000) // =1s

            // Set Progress Max
            progressMax = 25f

            // Set ProgressBar Color
            progressBarColor = Color.BLACK
            // or with gradient
            progressBarColorStart = Color.GRAY
            progressBarColorEnd = Color.RED
            progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set background ProgressBar Color
            backgroundProgressBarColor = Color.GRAY
            // or with gradient
            backgroundProgressBarColorStart = Color.WHITE
            backgroundProgressBarColorEnd = Color.GREEN
            backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM

            // Set Width
            progressBarWidth = 7f // in DP
            backgroundProgressBarWidth = 3f // in DP

            // Other
            roundBorder = true
            startAngle = 180f
            progressDirection = CircularProgressBar.ProgressDirection.TO_RIGHT
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question = mQuestionList!!.get(mCurrentPosition - 1)
        isNewQuestion =  true
        updateUI(question.type)

        defaultOptionsView()
        if (mCurrentPosition == mQuestionList!!.size) {
            binding.btnSubmit.text = "Finish"
        } else {
            binding.btnSubmit.text = "Go to next question"
        }

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition" + "/" + mQuestionList!!.size


        binding.tvQuestion.text = question.question
        binding.questionImageview.setImageBitmap(question.questionImage?.let {
            Constants.byteArrayToBitmap(
                it
            )
        })

//        binding.tvOptionOne.text = question.optionOne
//        binding.tvOptionTwo.text = question.optionTwo
//        binding.tvOptionThree.text = question.optionThree
//        binding.tvOptionFour.text = question.optionFour

        if (question.type == Constants.TEXT){

            val options = mutableListOf(
                question.optionOne,
                question.optionTwo,
                question.optionThree,
                question.optionFour
            )

// Shuffle the options
            options.shuffle()
            correctAnswerIndex = when (question.correctOption) {
                1 -> options.indexOf(question.optionOne)
                2 -> options.indexOf(question.optionTwo)
                3 -> options.indexOf(question.optionThree)
                4 -> options.indexOf(question.optionFour)
                else -> -1 // Handle the case when correctOption is not in the range 1-4
            }



// Set shuffled options to UI
            binding.tvOptionOne.text = options[0]
            binding.tvOptionTwo.text = options[1]
            binding.tvOptionThree.text = options[2]
            binding.tvOptionFour.text = options[3]

        } else if (question.type == Constants.IMAGE){

            val optionsImage = mutableListOf(
                question.optionOneImage,
                question.optionTwoImage,
                question.optionThreeImage,
                question.optionFourImage
            )

            optionsImage.shuffle()
            correctAnswerIndex = when (question.correctOption) {
                1 -> optionsImage.indexOf(question.optionOneImage)
                2 -> optionsImage.indexOf(question.optionTwoImage)
                3 -> optionsImage.indexOf(question.optionThreeImage)
                4 -> optionsImage.indexOf(question.optionFourImage)
                else -> -1 // Handle the case when correctOption is not in the range 1-4
            }

            binding.option1Imageview.setImageBitmap(optionsImage[0]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })
            binding.option2Imageview.setImageBitmap(optionsImage[1]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

            binding.option3Imageview.setImageBitmap(optionsImage[2]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })
            binding.option4Imageview.setImageBitmap(optionsImage[3]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })


        } else {

            val options = mutableListOf(
                question.optionOne,
                question.optionTwo,
                question.optionThree,
                question.optionFour
            )
            // Set shuffled options to UI
            binding.tvOptionOne.text = options[0]
            binding.tvOptionTwo.text = options[1]
            binding.tvOptionThree.text = options[2]
            binding.tvOptionFour.text = options[3]


            val optionsImage = mutableListOf(
                question.optionOneImage,
                question.optionTwoImage,
                question.optionThreeImage,
                question.optionFourImage
            )


            correctAnswerIndex = when (question.correctOption) {
                1 -> options.indexOf(question.optionOne)
                2 -> options.indexOf(question.optionTwo)
                3 -> options.indexOf(question.optionThree)
                4 -> options.indexOf(question.optionFour)
                else -> -1 // Handle the case when correctOption is not in the range 1-4
            }

            binding.option1Imageview.setImageBitmap(optionsImage[0]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })
            binding.option2Imageview.setImageBitmap(optionsImage[1]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

            binding.option3Imageview.setImageBitmap(optionsImage[2]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })
            binding.option4Imageview.setImageBitmap(optionsImage[3]?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

        }

        // Attach click listeners to each option
        binding.aLayout.setOnClickListener {
            checkAnswer(0, correctAnswerIndex)
        }
        binding.bLayout.setOnClickListener {
            checkAnswer(1, correctAnswerIndex)
        }
        binding.cd0.setOnClickListener {
            checkAnswer(2, correctAnswerIndex)
        }
        binding.dLayout.setOnClickListener {
            checkAnswer(3, correctAnswerIndex)
        }

        countDownTimer.cancel()
        timeLeftInMillis = TIMER
        countDownTimer.start()

//        if (music.isPlaying){
//            music.stop()
//        }
//        music.start()

    }

    // Function to check the answer
    private fun checkAnswer(selectedIndex: Int, correctAnswerIndex: Int) {
        if (isNewQuestion){
            if (selectedIndex == correctAnswerIndex) {
                // Correct answer
                answerView(selectedIndex + 1, R.drawable.correct_option_border_bg) // Add 1 to convert back to 1-based index
                CORRECT += 1
            } else {
                // Incorrect answer
                answerView(selectedIndex + 1, R.drawable.wrong_option_border_bg) // Add 1 to convert back to 1-based index
                // Highlight the correct answer
                answerView(correctAnswerIndex + 1, R.drawable.correct_option_border_bg) // Add 1 to convert back to 1-based index
            }
            isNewQuestion = false
        }
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
                checkAnswer(0, correctAnswerIndex)
            }
            R.id.bLayout -> {
                checkAnswer(1, correctAnswerIndex)
            }
            R.id.cLayout -> {
                checkAnswer(2, correctAnswerIndex)
            }
            R.id.dLayout -> {
                checkAnswer(3, correctAnswerIndex)
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
                            val intent = Intent(this, ScoreActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.correctOption != mSelectedOptionPosition) {
//                        checkAnswer(0, correctAnswerIndex)
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
        binding.tvTimer.text = secondsLeft.toString()
        binding.circularProgressBar.progress = secondsLeft.toFloat()
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