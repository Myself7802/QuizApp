package com.example.quizapp.helpers

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.quizapp.databinding.DialogPasswordBinding

class PasswordDialog(context: Context, private val onPasswordEntered: (String) -> Unit) : Dialog(context) {

    private lateinit var binding: DialogPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.passwordEditText.requestFocus()
        binding.okButton.setOnClickListener {
            val enteredPassword = binding.passwordEditText.text.toString()
            onPasswordEntered(enteredPassword)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}
