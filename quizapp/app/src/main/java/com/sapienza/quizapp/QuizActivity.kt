package com.sapienza.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

class QuizActivity: AppCompatActivity(), View.OnClickListener {
    var questionsList = ArrayList<ParseQuestions>()

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<ParseQuestions>? = null
    private var mSelectedOptionPosition: Int = 0
    private var correctAnswerCount = 0
    private var mUsername: String?  = null
    var totalQuestions: Int = 0

    var choicesList = ArrayList<ParseChoices>()
    lateinit var currentCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        currentCategory = intent.getStringExtra("CATEGORY_NAME").toString()
        readJSON()
        mUsername = intent.getStringExtra(Constants.USER_NAME)

        mQuestionList = questionsList
        setQuestion()
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

        text_progress.text = "$mCurrentPosition" + "/" + totalQuestions

        quiz_question.text = question.question

        if (question.img.isEmpty()) {
            quiz_image.setImageResource(R.drawable.placeholder);
        } else{
            Picasso.get().load(question.img).into(quiz_image)
        }

        Picasso.get().isLoggingEnabled = true
        Log.i("Image Details", ""+ question.img)
        text_options1.text = question.choices1.text
        text_options2.text = question.choices2.text
        text_options3.text = question.choices3.text
        text_options4.text = question.choices4.text

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
        when(v?.id){
            R.id.text_options1 -> {
                selectionOption(text_options1, question!!.choices1.isCorrect)
                disableAllButtons()
            }

            R.id.text_options2 -> {
                Log.i("CLicked Option: ","2")
                selectionOption(text_options2, question!!.choices2.isCorrect)
                disableAllButtons()
            }

            R.id.text_options3 -> {
                Log.i("CLicked Option: ","3")
                selectionOption(text_options3, question!!.choices3.isCorrect)
                disableAllButtons()
            }

            R.id.text_options4 -> {
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
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUsername)
                        intent.putExtra("Constants.CORRECT_ANSWERS", correctAnswerCount)
                        intent.putExtra("Constants.TOTAL_QUESTIONS", mQuestionList!!.size)
                        startActivity(intent)
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

    private fun readJSON() {
        var json: String? = null


        GlobalScope.launch { // launch a new coroutine in background and continue
            var client = OkHttpClient()

            try {
                fun run(url: String?) {
                    val localUrl: HttpUrl? = HttpUrl.parse(url)
                    val request = Request.Builder()
                        .url(localUrl)
                        .build()
                    client.newCall(request).execute().use { response -> json = response.body()?.string()  }
                    Log.i("Response", ""+client)
                }
                run("http://161.35.55.28:5000/api/questions/")

                //val inputStream: InputStream = assets.open("dummy_questions.json")
                //json = inputStream.bufferedReader().use { it.readText() }

                var jsonarr = JSONArray(json)
                Log.i("Respanse", ""+jsonarr)
                var inc: Int = 0

                (0 until jsonarr.length()).forEach{ _ ->

                    choicesList.clear()
                    var choicesInc: Int = 0
                    val questionId: String = jsonarr.getJSONObject(inc).getString("_id")
                    val description: String = jsonarr.getJSONObject(inc).getString("description")
                    val category: String = jsonarr.getJSONObject(inc).getString("category")
                    val image: String = jsonarr.getJSONObject(inc).getString("image")
                    val choices: JSONArray = jsonarr.getJSONObject(inc).getJSONArray("choices")

                    (0 until choices.length()).forEach{ _ ->
                        val isCorrect: Boolean = choices.getJSONObject(choicesInc).getBoolean("isCorrect")
                        val choiceText: String = choices.getJSONObject(choicesInc).getString("text")
                        choicesList.add(ParseChoices(isCorrect, choicesInc, choiceText))
                        choicesInc++
                    }

                    val que = ParseQuestions(questionId,
                        description,
                        category,
                        image,
                        choicesList[0],
                        choicesList[1],
                        choicesList[2],
                        choicesList[3]
                    )
                    if (category == currentCategory){
                        questionsList.add(que)
                        totalQuestions++
                    }


                    inc++

                }
            }catch (i: IOException){
                Log.i("esxep",""+i)
            }
        }
        runBlocking {
            delay(1000L) }

    }
}
