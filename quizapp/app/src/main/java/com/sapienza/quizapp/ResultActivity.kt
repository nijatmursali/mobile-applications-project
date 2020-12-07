package com.sapienza.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswerCount = intent.getIntExtra("Constants.CORRECT_ANSWERS", 0)
        val totalQuestions = intent.getIntExtra("Constants.TOTAL_QUESTIONS",0)
        var percentage = correctAnswerCount * 100 / totalQuestions

        tv_name.text = username
        if (percentage<25.0){
            tv_congratulations.text = "Sorry"
            iv_trophy.setImageResource(R.drawable.oops)
            tv_score.text = "Your score is $correctAnswerCount out of $totalQuestions"
        } else if (percentage in 25.0..75.0){
            tv_congratulations.text = "Welldone!"
            iv_trophy.setImageResource(R.drawable.great_job)
            tv_score.text = "Your score is $correctAnswerCount out of $totalQuestions"
        } else if (percentage in 75.0..100.0){
            tv_congratulations.text = "Congratulations"
            iv_trophy.setImageResource(R.drawable.ic_trophy)
            tv_score.text = "Your score is $correctAnswerCount out of $totalQuestions"
        }
        btn_finish.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}