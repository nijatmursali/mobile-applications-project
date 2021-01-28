package com.sapienza.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readJSON()

        val mainLinearLayout = findViewById<LinearLayout>(R.id.mLayout)


        (0 until categoryList.size).forEach{ _ ->

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        //params.setMargins(0,0,0,50)

        val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL


        //My Code
        val myCardView = androidx.cardview.widget.CardView(this)
            myCardView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            myCardView.setBackgroundResource(R.drawable.card_background)
            (myCardView.layoutParams as LinearLayout.LayoutParams).setMargins(30,0,30,30)
            myCardView.radius = 50f


        val myCardLinearLayout1 = LinearLayout(this)
            myCardLinearLayout1.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            (myCardLinearLayout1.layoutParams as LinearLayout.LayoutParams).setMargins(30,30,20,20)
            myCardLinearLayout1.orientation = LinearLayout.VERTICAL
//

        val myCardLinearLayout2 = LinearLayout(this)
            myCardLinearLayout2.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
//            (myCardLinearLayout2.layoutParams as LinearLayout.LayoutParams).setMargins(30,20,30,0)
            myCardLinearLayout2.orientation = LinearLayout.HORIZONTAL

        val myCardLinearLayout3 = LinearLayout(this)
            myCardLinearLayout3.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            myCardLinearLayout3.orientation = LinearLayout.VERTICAL

        val myCardRelativeLayout = RelativeLayout(this)
            myCardRelativeLayout.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        val myCardLinearLayout4 = LinearLayout(this)
            myCardLinearLayout4.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            myCardLinearLayout4.orientation = LinearLayout.HORIZONTAL

        val quote = TextView(this)
            quote.text = ""+categoryList[categoryCounter];
            quote.textSize = 24f
            quote.setTextColor(Color.WHITE)
            Log.i("Category: ", ""+categoryList[categoryCounter])

        val name = TextView(this)
            name.text = "We have listed several "+categoryList[categoryCounter]
            name.textSize = 15f
            name.setTextColor(Color.parseColor("#E0F2F1"))

        val img = ImageView(this)
            img.setImageResource(R.drawable.questionmark)
            img.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            img.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 210)

        val btn = Button(this)
            btn.text = ""+categoryList[categoryCounter]
            btn.textSize = 12f
            btn.setTextColor(0xFFFFFFFF.toInt())
            btn.setBackgroundColor(0xFF6200EE.toInt())
            btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            (btn.layoutParams as LinearLayout.LayoutParams).setMargins(0,30,0,0)
            btn.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("CATEGORY_NAME", btn.text as String)
                startActivity(intent)
                finish()
        }

            mainLinearLayout.addView(myCardView)
            myCardView.addView(myCardLinearLayout1)
            myCardLinearLayout1.addView(myCardLinearLayout2)
            myCardLinearLayout2.addView(myCardLinearLayout3)
            myCardLinearLayout3.addView(quote)
            myCardLinearLayout3.addView(name)
            myCardLinearLayout2.addView((myCardRelativeLayout))
            myCardRelativeLayout.addView(img)
            myCardLinearLayout1.addView(myCardLinearLayout4)
            myCardLinearLayout4.addView(btn)
            categoryCounter++
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


// URL Backup: http://161.35.55.28:5000/api/questions/