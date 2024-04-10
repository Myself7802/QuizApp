package com.example.quizapp.models

import android.os.Parcel
import android.os.Parcelable
import com.example.quizapp.helpers.Constants
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

//open class Question(
//    @PrimaryKey
//    var id: Long = 0, // Auto-generated ID
//    var question: String = "",
//    var questionImage: ByteArray? = null,
//    var optionOne: String = "",
//    var optionOneImage: ByteArray? = null,
//    var optionTwo: String = "",
//    var optionTwoImage: ByteArray? = null,
//    var optionThree: String = "",
//    var optionThreeImage: ByteArray? = null,
//    var optionFour: String = "",
//    var optionFourImage: ByteArray? = null,
//    var correctOption: Int = 0
//) : RealmObject

@PaperParcel
class Question(): RealmObject,PaperParcelable {

    @PrimaryKey
    var id: Long = System.currentTimeMillis() // Auto-generated ID
    var question: String = ""
    var questionImage: ByteArray? = null
    var optionOne: String = ""
    var optionOneImage: ByteArray? = null
    var optionTwo: String = ""
    var optionTwoImage: ByteArray? = null
    var optionThree: String = ""
    var optionThreeImage: ByteArray? = null
    var optionFour: String = ""
    var optionFourImage: ByteArray? = null
    var correctOption: Int = 0
    var type: String = Constants.TEXT

    companion object {
        @JvmField
        val CREATOR = PaperParcelQuestion.CREATOR
    }
}

