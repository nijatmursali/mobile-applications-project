package com.sapienza.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswerCount = intent.getStringExtra(Constants.CORRECT_ANSWERS)
        val totalQuestions = intent.getStringExtra(Constants.TOTAL_QUESTIONS)

        tv_name.text = username
        tv_score.text = "Your score is $correctAnswerCount out of $totalQuestions"

        btn_finish.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}