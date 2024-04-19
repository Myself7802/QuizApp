package com.example.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.quizapp.models.Question
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class QuestionViewModel : ViewModel() {

    private val realm: Realm by lazy {
        val config = RealmConfiguration.create(schema = setOf(Question::class))
        Realm.open(config)
    }

    fun saveQuestion(
        question: Question
    ) {
        realm.writeBlocking {
            copyToRealm(question)
        }
    }

    fun deleteQuestion(
        question: Question
    ) {
        realm.writeBlocking {
            val question1 = query<Question>("id == $0", question.id).find().first()
            delete(question1)
        }
    }

    fun updateQuestion(
        question: Question,
        sampleQuestion1: Question
    ) {
        realm.writeBlocking {
            val question1 = query<Question>("id == $0", question.id).find().first()
            question1.question = sampleQuestion1.question
            question1.optionOne = sampleQuestion1.optionOne
            question1.optionTwo = sampleQuestion1.optionTwo
            question1.optionThree = sampleQuestion1.optionThree
            question1.optionFour = sampleQuestion1.optionFour

            question1.questionImage = sampleQuestion1.questionImage
            question1.optionOneImage = sampleQuestion1.optionOneImage
            question1.optionTwoImage = sampleQuestion1.optionTwoImage
            question1.optionThreeImage = sampleQuestion1.optionThreeImage
            question1.optionFourImage = sampleQuestion1.optionFourImage

            question1.correctOption = sampleQuestion1.correctOption
            question1.type = sampleQuestion1.type
        }
    }

    fun getQuestions(language: String): List<Question> {
        val questions: RealmResults<Question> = realm.query<Question>("language == $0",language).find()
       val questionList: List<Question> = realm.copyFromRealm(questions)

        Log.d("QuestionData", "size: ${questionList.size}")
        for (question in questionList) {
            Log.d("QuestionData", "ID: ${question.id}")
            Log.d("QuestionData", "Question: ${question.question}")
            Log.d("QuestionData", "QuestionImage: ${question.questionImage}")
            Log.d("QuestionData", "Option One: ${question.optionOne}")
            Log.d("QuestionData", "Option One Image: ${question.optionOneImage}")
            Log.d("QuestionData", "Option Two: ${question.optionTwo}")
            Log.d("QuestionData", "Option Two Image: ${question.optionTwoImage}")
            Log.d("QuestionData", "Option Three: ${question.optionThree}")
            Log.d("QuestionData", "Option Three Image: ${question.optionThreeImage}")
            Log.d("QuestionData", "Option Four: ${question.optionFour}")
            Log.d("QuestionData", "Option Four Image: ${question.optionFourImage}")
            Log.d("QuestionData", "Correct Option: ${question.correctOption}")
            Log.d("QuestionData", "Type : ${question.type}")
            Log.d("QuestionData", "Language : ${question.language}")
            Log.d("QuestionData", "-----------------------")
        }

        return questionList
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}