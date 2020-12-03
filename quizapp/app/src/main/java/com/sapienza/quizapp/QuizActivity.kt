package com.sapienza.quizapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity: AppCompatActivity(), View.OnClickListener{

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<ParseQuestions>? = null
    private var mSelectedOptionPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mQuestionList = Constants.getQuestions()
        //setQuestion()
        text_options1.setOnClickListener(this)
        text_options2.setOnClickListener(this)
        text_options3.setOnClickListener(this)
        text_options4.setOnClickListener(this)

        submit_btn.setOnClickListener(this)
    }

    private fun setQuestion(){

        val question = mQuestionList!!.get(mCurrentPosition-1)
        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size){
            submit_btn.text = "FINISH"
        } else {
            submit_btn.text = "SUBMIT"
        }

        progress_bar.progress = mCurrentPosition

        text_progress.text = "$mCurrentPosition" + "/" + progress_bar.max

        quiz_question.text = question!!.question
        quiz_image.setImageResource(question!!.img)

        text_options1.text = question!!.option1
        text_options2.text = question!!.option2
        text_options3.text = question!!.option3
        text_options4.text = question!!.option4

    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        options.add(0, text_options1)
        options.add(1, text_options2)
        options.add(2, text_options3)
        options.add(3, text_options4)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.text_options1 -> {
                selectedOptionView(text_options1, 1)
            }

            R.id.text_options2 -> {
                selectedOptionView(text_options2, 2)
            }

            R.id.text_options3 -> {
                selectedOptionView(text_options3, 3)
            }

            R.id.text_options4 -> {
                selectedOptionView(text_options4, 4)
            }

            R.id.submit_btn -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        } else -> {
                        Toast.makeText(this, "You have successfully completed the Quiz", Toast.LENGTH_SHORT).show()
                    }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition -1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size){
                        submit_btn.text = "FINISH"
                    } else {
                        submit_btn.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> {
                text_options1.background = ContextCompat.getDrawable(this, drawableView)
            }

            2 -> {
                text_options2.background = ContextCompat.getDrawable(this, drawableView)
            }

            3 -> {
                text_options3.background = ContextCompat.getDrawable(this, drawableView)
            }

            4 -> {
                text_options4.background = ContextCompat.getDrawable(this, drawableView)
            }


        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }
}