package com.example.quizapp.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityAdminBinding
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Constants.PICK_IMAGE_REQUEST_OPTION_1
import com.example.quizapp.helpers.Constants.PICK_IMAGE_REQUEST_OPTION_2
import com.example.quizapp.helpers.Constants.PICK_IMAGE_REQUEST_OPTION_3
import com.example.quizapp.helpers.Constants.PICK_IMAGE_REQUEST_OPTION_4
import com.example.quizapp.helpers.Constants.PICK_IMAGE_REQUEST_QUESTION
import com.example.quizapp.helpers.Constants.byteArrayToBitmap
import com.example.quizapp.helpers.Constants.option1Image
import com.example.quizapp.helpers.Constants.option2Image
import com.example.quizapp.helpers.Constants.option3Image
import com.example.quizapp.helpers.Constants.option4Image
import com.example.quizapp.helpers.Constants.questionImage
import com.example.quizapp.models.Question
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val question1 = intent.getParcelableExtra<Question>("question")
        if (question1 != null) {
            Log.d("QuestionData", "ID: ${question1.id}")
            Log.d("QuestionData", "Question: ${question1.question}")
            Log.d("QuestionData", "QuestionImage: ${question1.questionImage}")
            Log.d("QuestionData", "Option One: ${question1.optionOne}")
            Log.d("QuestionData", "Option One Image: ${question1.optionOneImage}")
            Log.d("QuestionData", "Option Two: ${question1.optionTwo}")
            Log.d("QuestionData", "Option Two Image: ${question1.optionTwoImage}")
            Log.d("QuestionData", "Option Three: ${question1.optionThree}")
            Log.d("QuestionData", "Option Three Image: ${question1.optionThreeImage}")
            Log.d("QuestionData", "Option Four: ${question1.optionFour}")
            Log.d("QuestionData", "Option Four Image: ${question1.optionFourImage}")
            Log.d("QuestionData", "Correct Option: ${question1.correctOption}")
            Log.d("QuestionData", "Type : ${question1.type}")
            Log.d("QuestionData", "-----------------------")

            updateUI(question1.type)

            when (question1.correctOption) {
                1 -> binding.radioButtonA.isChecked = true
                2 -> binding.radioButtonB.isChecked = true
                3 -> binding.radioButtonC.isChecked = true
                4 -> binding.radioButtonD.isChecked = true
                else -> binding.radioButtonA.isChecked = true
            }
            binding.question.setText(question1.question)
            binding.opetion1.setText(question1.optionOne)
            binding.opetion2.setText(question1.optionTwo)
            binding.opetion3.setText(question1.optionThree)
            binding.opetion4.setText(question1.optionFour)

            binding.questionImageview.setImageBitmap(question1.questionImage?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

            binding.option1Imageview.setImageBitmap(question1.optionOneImage?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

            binding.option2Imageview.setImageBitmap(question1.optionTwoImage?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

            binding.option3Imageview.setImageBitmap(question1.optionThreeImage?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })

            binding.option4Imageview.setImageBitmap(question1.optionFourImage?.let {
                Constants.byteArrayToBitmap(
                    it
                )
            })


        } else {
            updateUI(Constants.TEXT)
        }

        binding.textButton.setOnClickListener {
            Constants.type = Constants.TEXT
            updateUI(Constants.TEXT)
        }

        binding.imageButton.setOnClickListener {
            Constants.type = Constants.IMAGE
            updateUI(Constants.IMAGE)
        }

        binding.bothButton.setOnClickListener {
            Constants.type = Constants.BOTH
            updateUI(Constants.BOTH)
        }

        binding.questionImageview.setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_QUESTION)
        }

        binding.option1Imageview.setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_OPTION_1)
        }

        binding.option2Imageview.setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_OPTION_2)
        }

        binding.option3Imageview.setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_OPTION_3)
        }

        binding.option4Imageview.setOnClickListener {
            openImagePicker(PICK_IMAGE_REQUEST_OPTION_4)
        }

        binding.view.setOnClickListener {
            val intent = Intent(this, QuestionViewActivity::class.java)
            startActivity(intent)
        }


        binding.submit.setOnClickListener {

            if (Constants.type == Constants.TEXT){
                val sampleQuestion1 = Question().apply {

                    question = binding.question.text.toString()
                    optionOne = binding.opetion1.text.toString()
                    optionTwo = binding.opetion2.text.toString()
                    optionThree = binding.opetion3.text.toString()
                    optionFour = binding.opetion4.text.toString()

                    val selectedOptionId = binding.radioGroup.checkedRadioButtonId
                    val selectedOption = when (selectedOptionId) {
                        R.id.radioButtonA -> {
                            correctOption = 1
                            "A"
                        }
                        R.id.radioButtonB -> {
                            correctOption = 2
                            "B"
                        }
                        R.id.radioButtonC -> {
                            correctOption = 3
                            "C"
                        }
                        R.id.radioButtonD -> {
                            correctOption = 4
                            "D"
                        }
                        else -> {
                            correctOption = 1
                            "A"
                        }
                    }

                    type = Constants.type
                }

                if (question1!=null){
                    viewModel.updateQuestion(question1,sampleQuestion1)
                } else{
                    viewModel.saveQuestion(sampleQuestion1);
                }
                onBackPressed()

            } else if (Constants.type == Constants.IMAGE){
                val sampleQuestion1 = Question().apply {
                    questionImage = Constants.questionImage
                    optionOneImage = Constants.option1Image
                    optionTwoImage = Constants.option2Image
                    optionThreeImage = Constants.option3Image
                    optionFourImage = Constants.option4Image

                    val selectedOptionId = binding.radioGroup.checkedRadioButtonId
                    val selectedOption = when (selectedOptionId) {
                        R.id.radioButtonA -> {
                            correctOption = 1
                            "A"
                        }
                        R.id.radioButtonB -> {
                            correctOption = 2
                            "B"
                        }
                        R.id.radioButtonC -> {
                            correctOption = 3
                            "C"
                        }
                        R.id.radioButtonD -> {
                            correctOption = 4
                            "D"
                        }
                        else -> {
                            correctOption = 1
                            "A"
                        }
                    }

                    type = Constants.type
                }
                if (question1!=null){
                    viewModel.updateQuestion(question1,sampleQuestion1)
                } else{
                    viewModel.saveQuestion(sampleQuestion1);
                }
                onBackPressed()
            } else{
                val sampleQuestion1 = Question().apply {

                    question = binding.question.text.toString()
                    // Set other properties accordingly
                    optionOne = binding.opetion1.text.toString()
                    optionTwo = binding.opetion2.text.toString()
                    optionThree = binding.opetion3.text.toString()
                    optionFour = binding.opetion4.text.toString()
                    // Set image byte arrays to null
                    questionImage = Constants.questionImage
                    optionOneImage = Constants.option1Image
                    optionTwoImage = Constants.option2Image
                    optionThreeImage = Constants.option3Image
                    optionFourImage = Constants.option4Image

                    val selectedOptionId = binding.radioGroup.checkedRadioButtonId
                    val selectedOption = when (selectedOptionId) {
                        R.id.radioButtonA -> {
                            correctOption = 1
                            "A"
                        }
                        R.id.radioButtonB -> {
                            correctOption = 2
                            "B"
                        }
                        R.id.radioButtonC -> {
                            correctOption = 3
                            "C"
                        }
                        R.id.radioButtonD -> {
                            correctOption = 4
                            "D"
                        }
                        else -> {
                            correctOption = 1
                            "A"
                        }
                    }

                    type = Constants.type
                }

                if (question1!=null){
                    viewModel.updateQuestion(question1,sampleQuestion1)
                } else{
                    viewModel.saveQuestion(sampleQuestion1);
                }
                onBackPressed()
            }

        }

    }

    private fun updateUI(type: String) {
        if (type == Constants.TEXT){
            binding.layoutQuestion.visibility = View.VISIBLE
            binding.cd0.visibility = View.GONE
            binding.questionImageview.visibility = View.GONE

            binding.layoutOpetion1.visibility = View.VISIBLE
            binding.layoutOpetion2.visibility = View.VISIBLE
            binding.layoutOpetion3.visibility = View.VISIBLE
            binding.layoutOpetion4.visibility = View.VISIBLE

            binding.cd1.visibility = View.GONE
            binding.cd2.visibility = View.GONE
            binding.cd3.visibility = View.GONE
            binding.cd4.visibility = View.GONE

        } else if (type == Constants.IMAGE){
            binding.layoutQuestion.visibility = View.GONE
            binding.cd0.visibility = View.VISIBLE
            binding.questionImageview.visibility = View.VISIBLE

            binding.layoutOpetion1.visibility = View.GONE
            binding.layoutOpetion2.visibility = View.GONE
            binding.layoutOpetion3.visibility = View.GONE
            binding.layoutOpetion4.visibility = View.GONE

            binding.cd1.visibility = View.VISIBLE
            binding.cd2.visibility = View.VISIBLE
            binding.cd3.visibility = View.VISIBLE
            binding.cd4.visibility = View.VISIBLE
        } else{
            binding.layoutQuestion.visibility = View.VISIBLE
            binding.cd0.visibility = View.VISIBLE
            binding.questionImageview.visibility = View.VISIBLE

            binding.layoutOpetion1.visibility = View.VISIBLE
            binding.layoutOpetion2.visibility = View.VISIBLE
            binding.layoutOpetion3.visibility = View.VISIBLE
            binding.layoutOpetion4.visibility = View.VISIBLE

            binding.cd1.visibility = View.VISIBLE
            binding.cd2.visibility = View.VISIBLE
            binding.cd3.visibility = View.VISIBLE
            binding.cd4.visibility = View.VISIBLE
        }
    }

    fun openImagePicker(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_QUESTION && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                // Convert the selected image URI to a byte array
                val imageByteArray = uriToByteArray(selectedImageUri)
                questionImage = imageByteArray
                binding.questionImageview.setImageBitmap(questionImage?.let { it1 ->
                    byteArrayToBitmap(
                        it1
                    )
                })

                // Now you have the imageByteArray, you can do whatever you want with it
                // For example, save it to Realm or display it in an ImageView
            }
        }

        else if (requestCode == PICK_IMAGE_REQUEST_OPTION_1 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                // Convert the selected image URI to a byte array
                val imageByteArray = uriToByteArray(selectedImageUri)
                option1Image = imageByteArray
                binding.option1Imageview.setImageBitmap(option1Image?.let { it1 ->
                    byteArrayToBitmap(
                        it1
                    )
                })
            }
        }

        else if (requestCode == PICK_IMAGE_REQUEST_OPTION_2 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                // Convert the selected image URI to a byte array
                val imageByteArray = uriToByteArray(selectedImageUri)
                option2Image = imageByteArray
                binding.option2Imageview.setImageBitmap(option2Image?.let { it1 ->
                    byteArrayToBitmap(
                        it1
                    )
                })
            }
        }

        else if (requestCode == PICK_IMAGE_REQUEST_OPTION_3 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                // Convert the selected image URI to a byte array
                val imageByteArray = uriToByteArray(selectedImageUri)
                option3Image = imageByteArray
                binding.option3Imageview.setImageBitmap(option3Image?.let { it1 ->
                    byteArrayToBitmap(
                        it1
                    )
                })
            }
        }

        else if (requestCode == PICK_IMAGE_REQUEST_OPTION_4 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                // Convert the selected image URI to a byte array
                val imageByteArray = uriToByteArray(selectedImageUri)
                option4Image = imageByteArray
                binding.option4Imageview.setImageBitmap(option4Image?.let { it1 ->
                    byteArrayToBitmap(
                        it1
                    )
                })
            }
        }
    }

    fun uriToByteArray(uri: Uri): ByteArray? {
        val inputStream = contentResolver.openInputStream(uri)
        return inputStream?.buffered()?.use { it.readBytes() }
    }


}