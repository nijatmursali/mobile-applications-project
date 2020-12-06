package com.sapienza.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var categoryList = ArrayList<String>()
    var categoryCounter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readJSON()

        val mainLinearLayout = findViewById<LinearLayout>(R.id.mLayout)


        (0 until categoryList.size).forEach{ _ ->

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(30,30,30,30)

        val cardLinearLayout = LinearLayout(this)
        cardLinearLayout.orientation = LinearLayout.VERTICAL
        cardLinearLayout.layoutParams = params



        val cardView = CardView(this)
        cardView.radius = 15f
        cardView.setCardBackgroundColor(Color.parseColor("#009688"))
        cardView.setContentPadding(36,36,36,36)
        cardView.setBackgroundResource(R.drawable.card_background)
        cardView.layoutParams = params
        cardView.cardElevation = 30f

        val quote = TextView(this)
        quote.text = ""+categoryList[categoryCounter];
        quote.textSize = 24f
        quote.setTextColor(Color.WHITE)
        quote.setTypeface(Typeface.SANS_SERIF,Typeface.NORMAL)
        Log.i("Category: ", ""+categoryList[categoryCounter])
        val name = TextView(this)
        name.text = "- Thomas A. Edison"
        name.textSize = 16f
        name.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC)
        name.setTextColor(Color.parseColor("#E0F2F1"))

        val btn = Button(this)
        btn.text = ""+categoryList[categoryCounter]
        btn.textSize = 15f
        btn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("CATEGORY_NAME", btn.text as String)
            startActivity(intent)
            finish()
        }

        cardLinearLayout.addView(quote)
        cardLinearLayout.addView(name)
        cardLinearLayout.addView(btn)
        cardView.addView(cardLinearLayout)




        mainLinearLayout.addView(cardView)
            categoryCounter++
        }
        btn_category.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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
                var inc: Int = 0
                (0 until jsonarr.length()).forEach{ _ ->
                    val category: String = jsonarr.getJSONObject(inc).getString("category")
                    if (!categoryList.contains(category)){
                        categoryList.add(category)
                    }
                    inc++
                }

                Log.i("CategoryCheck", ""+categoryList)


            }catch (i: IOException){
                Log.i("esxep",""+i)
            }
        }
        runBlocking {
            delay(1000L) }

    }
}