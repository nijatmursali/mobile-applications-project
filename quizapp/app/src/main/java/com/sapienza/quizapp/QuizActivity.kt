package com.sapienza.quizapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
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
    private var correctAnswerCount = 0

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
            submit_btn.text = "GO TO NEXT QUESTION"
        }

        progress_bar.progress = mCurrentPosition

        text_progress.text = "$mCurrentPosition" + "/" + progress_bar.max

        quiz_question.text = question!!.question
        quiz_image.setImageResource(question!!.img)

        text_options1.text = question!!.choices1.text
        text_options2.text = question!!.choices2.text
        text_options3.text = question!!.choices3.text
        text_options4.text = question!!.choices4.text

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
        val question = mQuestionList?.get(mCurrentPosition -1)
        //mCurrentPosition ++
        when(v?.id){
            R.id.text_options1 -> {
                //selectedOptionView(text_options1, 1)
                Log.i("CLicked Option: ","1 & choice id:"+question!!.choices1.id+" with choice text: "+question!!.choices1.text+" is: "+question.choices1.isCorrect)
                selectionOption(text_options1, question.choices1.isCorrect)
                Log.i("mCurrentPosition: ", ""+mCurrentPosition)
                disableAllButtons()
            }

            R.id.text_options2 -> {
               // selectedOptionView(text_options2, 2)
                Log.i("CLicked Option: ","2")
                selectionOption(text_options2, question!!.choices2.isCorrect)
                disableAllButtons()
            }

            R.id.text_options3 -> {
               // selectedOptionView(text_options3, 3)
                Log.i("CLicked Option: ","3")
                selectionOption(text_options3, question!!.choices3.isCorrect)
                disableAllButtons()
            }

            R.id.text_options4 -> {
               // selectedOptionView(text_options4, 4)
                Log.i("CLicked Option: ","4")
                selectionOption(text_options4, question!!.choices4.isCorrect)
                disableAllButtons()
            }

            R.id.submit_btn -> {
                    enableAllButtons()
                    mCurrentPosition ++

                    when{
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        } else -> {
                        Toast.makeText(this, "You have successfully completed the Quiz, Your score is: "+correctAnswerCount, Toast.LENGTH_SHORT).show()
                    }
                    }

                    if (mCurrentPosition == mQuestionList!!.size){
                        submit_btn.text = "FINISH"
                    }
                    mSelectedOptionPosition = 0

            }
        }
    }

    private fun disableAllButtons(){
        text_options1.isEnabled = false
        text_options2.isEnabled = false
        text_options3.isEnabled = false
        text_options4.isEnabled = false
    }

    private fun enableAllButtons(){
        text_options1.isEnabled = true
        text_options2.isEnabled = true
        text_options3.isEnabled = true
        text_options4.isEnabled = true
    }

    private fun selectionOption(tv: TextView, isCorrect: Boolean){
        if (isCorrect){
            tv.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            correctAnswerCount++
        } else {
            tv.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
        }
    }
}