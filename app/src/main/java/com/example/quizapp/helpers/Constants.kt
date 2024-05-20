package com.example.quizapp.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.quizapp.models.Question

object Constants {

    val PICK_IMAGE_REQUEST_QUESTION = 0
    val PICK_IMAGE_REQUEST_OPTION_1 = 1
    val PICK_IMAGE_REQUEST_OPTION_2 = 2
    val PICK_IMAGE_REQUEST_OPTION_3 = 3
    val PICK_IMAGE_REQUEST_OPTION_4 = 4

    var questionImage: ByteArray? = null
    var option1Image: ByteArray? = null
    var option2Image: ByteArray? = null
    var option3Image: ByteArray? = null
    var option4Image: ByteArray? = null

    var TEXT = "Text"
    var IMAGE = "Image"
    var BOTH = "Both"
    var type = TEXT

    var ADMIN_PASSWORD = "swaminarayan"

    var TIMER :Long = 25000
    var CORRECT :Int = 0
    var NUMBER_OF_QUESTION :Int = 0

    var LANGUAGE = "KIDS_ENGLISH"
    var ADMIN_LANGUAGE = "KIDS_ENGLISH"


    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

//    fun getQuestions(): ArrayList<Question> {
//        val questionsList = ArrayList<Question>()
//
//        val question1 = Question(
//            1,
//            "Which internet company began life as an online bookstore called 'Cadabra' ?",
//            "ebay",
//            "Shopify",
//            "Amazon",
//            "Overstock",
//            3
//        )
//        questionsList.add(question1)
//
//        val question2 = Question(
//            1,
//            "Which of the following languages is used as a scripting language in the Unity 3D game engine?",
//            "Java",
//            "C#",
//            "C++",
//            "Objective-C",
//            2
//        )
//        questionsList.add(question2)
//
//        val question3 = Question(
//            1,
//            "Which of these people was NOT a founder of Apple Inc?",
//            "Jonathan Ive",
//            "Steve Jobs",
//            "Ronald Wayne",
//            "Steve Wozniak",
//            1
//        )
//        questionsList.add(question3)
//
//        val question4 = Question(
//            1,
//            "What does the term GPU stand for?",
//            "Graphite Producing Unit",
//            "Gaming Processor Unit",
//            "Graphical Proprietary Unit",
//            "Graphics Processing Unit",
//            4
//        )
//        questionsList.add(question4)
//
//        val question5 = Question(
//            1,
//            "Moore's law originally stated that the number of transistors on a microprocessor chip would double every...",
//            "Year",
//            "Four Years",
//            "Two Years",
//            "Eight Years",
//            1
//        )
//        questionsList.add(question5)
//
//        val question6 = Question(
//            1,
//            "What five letter word is the motto of the IBM Computer company?",
//            "Click",
//            "Logic",
//            "Pixel",
//            "Think",
//            4
//        )
//        questionsList.add(question6)
//
//        val question7 = Question(
//            1,
//            "In programming, the ternary operator is mostly defined with what symbol(s)?",
//            "??",
//            "if then",
//            "?:",
//            "?",
//            3
//        )
//        questionsList.add(question7)
//
//        val question8 = Question(
//            1,
//            "On which computer hardware device is the BIOS chip located?",
//            "Motherboard",
//            "Hard Disk Drive",
//            "Central Processing Unit",
//            "Graphics Processing Unit",
//            1
//        )
//        questionsList.add(question8)
//
//        val question9 = Question(
//            1,
//            "What did the name of the Tor Anonymity Network orignially stand for?",
//            "The Only Router",
//            "The Orange Router",
//            "The Ominous Router",
//            "The Onion Router",
//            4
//        )
//        questionsList.add(question9)
//
//        val question10 = Question(
//            1,
//            "What was the first Android version specifically optimized for tablets?",
//            "Eclair",
//            "Honeycomb",
//            "Marshmellow",
//            "Froyo",
//            2
//        )
//        questionsList.add(question10)
//        return questionsList
//    }
}