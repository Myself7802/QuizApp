package com.example.quizapp.helpers

import com.example.quizapp.models.Question

public interface OnChangeClickListener {
    fun onDeleteClick(question: Question, pos: Int)
    fun onEditClick(question: Question, pos: Int)
}