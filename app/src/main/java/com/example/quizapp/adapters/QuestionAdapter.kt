package com.example.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.helpers.Constants
import com.example.quizapp.helpers.OnChangeClickListener
import com.example.quizapp.models.Question
import com.example.quizapp.viewmodel.QuestionViewModel

class QuestionAdapter(private val questions: MutableList<Question>, private val listener: OnChangeClickListener) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.question)
        val questionImageView: ImageView = itemView.findViewById(R.id.questionImageview)

        val opetion1: TextView = itemView.findViewById(R.id.opetion1)
        val opetion2: TextView = itemView.findViewById(R.id.opetion2)
        val opetion3: TextView = itemView.findViewById(R.id.opetion3)
        val opetion4: TextView = itemView.findViewById(R.id.opetion4)

        val answer: TextView = itemView.findViewById(R.id.answer)

        val option1Imageview: ImageView = itemView.findViewById(R.id.option1Imageview)
        val option2Imageview: ImageView = itemView.findViewById(R.id.option2Imageview)
        val option3Imageview: ImageView = itemView.findViewById(R.id.option3Imageview)
        val option4Imageview: ImageView = itemView.findViewById(R.id.option4Imageview)

        val cd0: CardView = itemView.findViewById(R.id.cd0)
        val cd1: CardView = itemView.findViewById(R.id.cd1)
        val cd2: CardView = itemView.findViewById(R.id.cd2)
        val cd3: CardView = itemView.findViewById(R.id.cd3)
        val cd4: CardView = itemView.findViewById(R.id.cd4)

        val edit: Button = itemView.findViewById(R.id.edit)
        val delete: Button = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val currentQuestion = questions[position]
        holder.question.text = currentQuestion.question

        val correctOptionText = when (currentQuestion.correctOption) {
            1 -> "A"
            2 -> "B"
            3 -> "C"
            4 -> "D"
            else -> "A"
        }

        holder.answer.text = "Right Answer: $correctOptionText"

        holder.delete.setOnClickListener {
            questions.removeAt(position)
            notifyItemRemoved(position)
            listener.onDeleteClick(currentQuestion,position)
        }

        holder.edit.setOnClickListener {
            listener.onEditClick(currentQuestion,position)
        }

        // Handle visibility of ImageView based on question type
        when (currentQuestion.type) {
            Constants.TEXT -> {
                holder.question.visibility = View.VISIBLE
                holder.cd0.visibility = View.GONE

                holder.opetion1.visibility = View.VISIBLE
                holder.opetion2.visibility = View.VISIBLE
                holder.opetion3.visibility = View.VISIBLE
                holder.opetion4.visibility = View.VISIBLE

                holder.cd1.visibility = View.GONE
                holder.cd2.visibility = View.GONE
                holder.cd3.visibility = View.GONE
                holder.cd4.visibility = View.GONE

                holder.question.text = currentQuestion.question
                holder.opetion1.text = currentQuestion.optionOne
                holder.opetion2.text = currentQuestion.optionTwo
                holder.opetion3.text = currentQuestion.optionThree
                holder.opetion4.text = currentQuestion.optionFour
            }
            Constants.IMAGE -> {
                holder.question.visibility = View.GONE
                holder.cd0.visibility = View.VISIBLE

                holder.opetion1.visibility = View.GONE
                holder.opetion2.visibility = View.GONE
                holder.opetion3.visibility = View.GONE
                holder.opetion4.visibility = View.GONE

                holder.cd1.visibility = View.VISIBLE
                holder.cd2.visibility = View.VISIBLE
                holder.cd3.visibility = View.VISIBLE
                holder.cd4.visibility = View.VISIBLE
                // Load image into ImageView from currentQuestion.questionImage

                holder.questionImageView.setImageBitmap(currentQuestion.questionImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })

                holder.option1Imageview.setImageBitmap(currentQuestion.optionOneImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })
                holder.option2Imageview.setImageBitmap(currentQuestion.optionTwoImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })

                holder.option3Imageview.setImageBitmap(currentQuestion.optionThreeImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })
                holder.option4Imageview.setImageBitmap(currentQuestion.optionFourImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })





            }
            Constants.BOTH -> {
                holder.question.visibility = View.VISIBLE
                holder.cd0.visibility = View.VISIBLE

                holder.opetion1.visibility = View.VISIBLE
                holder.opetion2.visibility = View.VISIBLE
                holder.opetion3.visibility = View.VISIBLE
                holder.opetion4.visibility = View.VISIBLE

                holder.cd1.visibility = View.VISIBLE
                holder.cd2.visibility = View.VISIBLE
                holder.cd3.visibility = View.VISIBLE
                holder.cd4.visibility = View.VISIBLE
                // Load image into ImageView from currentQuestion.questionImage

                holder.question.text = currentQuestion.question
                holder.opetion1.text = currentQuestion.optionOne
                holder.opetion2.text = currentQuestion.optionTwo
                holder.opetion3.text = currentQuestion.optionThree
                holder.opetion4.text = currentQuestion.optionFour

                holder.questionImageView.setImageBitmap(currentQuestion.questionImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })

                holder.option1Imageview.setImageBitmap(currentQuestion.optionOneImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })
                holder.option2Imageview.setImageBitmap(currentQuestion.optionTwoImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })

                holder.option3Imageview.setImageBitmap(currentQuestion.optionThreeImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })
                holder.option4Imageview.setImageBitmap(currentQuestion.optionFourImage?.let {
                    Constants.byteArrayToBitmap(
                        it
                    )
                })

            }
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }
}
