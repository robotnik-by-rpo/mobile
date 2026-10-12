package com.example.sum

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View)    {
        val etA = findViewById<EditText>(R.id.numA)
        val etB = findViewById<EditText>(R.id.numB)
        val tvSum = findViewById<TextView>(R.id.sum)

        val strA = etA.text.toString().ifEmpty { "0" }
        val strB = etB.text.toString().ifEmpty { "0" }
        val sum: Float = if (!strA.contains(".") && !strB.contains(".")) {
            (strA.toInt() + strB.toInt()).toFloat()
        } else {
            strA.toFloat() + strB.toFloat()
        }
        
        val strSum = sum.toString()
        tvSum.text = strSum
    }
}