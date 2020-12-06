package com.sapienza.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var CATEGORY_NAME = intent.getStringExtra("CATEGORY_NAME")

        start.setOnClickListener(){
            if(et_name.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra(Constants.USER_NAME, et_name.text.toString())
                intent.putExtra("CATEGORY_NAME", CATEGORY_NAME)
                startActivity(intent)
                finish()
            }
        }
    }
}