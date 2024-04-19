package com.example.quizapp.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
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
import com.example.quizapp.databinding.DialogAdminBinding
import com.example.quizapp.databinding.DialogBinding
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.Constants.ADMIN_LANGUAGE
import com.example.quizapp.helpers.Constants.ADMIN_PASSWORD
import com.example.quizapp.helpers.Constants.LANGUAGE
import com.example.quizapp.helpers.PasswordDialog
import com.example.quizapp.models.Question
import com.example.quizapp.viewmodel.QuestionVideModelFactory
import com.example.quizapp.viewmodel.QuestionViewModel

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding
    private lateinit var dialogBinding: DialogBinding
    private lateinit var dialogBinding2: DialogAdminBinding
    private lateinit var dialog: Dialog
    private lateinit var dialog2: Dialog
    private lateinit var viewModel: QuestionViewModel
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, QuestionVideModelFactory())[QuestionViewModel::class.java]
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        createDialog()
        createDialog2()

        binding.admin.setOnClickListener {
//            dialog2.show()
            showPasswordDialog()
        }

        binding.kids.setOnClickListener {
            type = "kids"
            dialog.show()
        }

        binding.adult.setOnClickListener {
            type = "adult"
            dialog.show()
        }

        dialogBinding.englishTextView.setOnClickListener {
            if (type == "kids"){
                LANGUAGE = "KIDS_ENGLISH"
            } else {
                LANGUAGE = "ADULT_ENGLISH"
            }
            nextActivity(LANGUAGE)
        }

        dialogBinding.gujaratiTextView.setOnClickListener {
            if (type == "kids"){
                LANGUAGE = "KIDS_GUJARATI"
            } else {
                LANGUAGE = "ADULT_GUJARATI"
            }
            nextActivity(LANGUAGE)
        }

        dialogBinding.hindiTextView.setOnClickListener {
            if (type == "kids"){
                LANGUAGE = "KIDS_HINDI"
            } else {
                LANGUAGE = "ADULT_HINDI"
            }
            nextActivity(LANGUAGE)
        }

        dialogBinding2.englishKids.setOnClickListener {
            goToAdmin("KIDS_ENGLISH")
        }

        dialogBinding2.englishAdult.setOnClickListener {
            goToAdmin("ADULT_ENGLISH")
        }

        dialogBinding2.gujaratiKids.setOnClickListener {
            goToAdmin("KIDS_GUJARATI")
        }

        dialogBinding2.gujaratiAdult.setOnClickListener {
            goToAdmin("ADULT_GUJARATI")
        }

        dialogBinding2.hindiKids.setOnClickListener {
            goToAdmin("KIDS_HINDI")
        }

        dialogBinding2.hindiAdult.setOnClickListener {
            goToAdmin("ADULT_HINDI")
        }
    }

    private fun goToAdmin(language: String) {
        val intent = Intent(this, QuestionViewActivity::class.java)
        ADMIN_LANGUAGE = language
        startActivity(intent)
        dialog2.dismiss()
    }

    private fun nextActivity(language: String) {
        val list = viewModel.getQuestions(LANGUAGE)
        if (list.size>0){
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(this@SelectActivity,"Comming soon...", Toast.LENGTH_SHORT).show()
        }
        dialog.dismiss()
    }


    private fun createDialog() {
        // Inflate the custom dialog layout using view binding
        dialogBinding = DialogBinding.inflate(LayoutInflater.from(this))
        val dialogView = dialogBinding.root

        // Create custom dialog
        dialog = Dialog(this)
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    private fun createDialog2() {
        // Inflate the custom dialog layout using view binding
        dialogBinding2 = DialogAdminBinding.inflate(LayoutInflater.from(this))
        val dialogView2 = dialogBinding2.root

        // Create custom dialog
        dialog2 = Dialog(this)
        dialog2.setContentView(dialogView2)
        dialog2.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    private fun showPasswordDialog() {
        val passwordDialog = PasswordDialog(this) { enteredPassword ->
            if (enteredPassword == ADMIN_PASSWORD) {
                // Password is correct, navigate to the second activity
                dialog2.show()
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }
        passwordDialog.show()
    }
}