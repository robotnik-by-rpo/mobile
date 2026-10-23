package com.example.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class GameActivity : AppCompatActivity() {

    var begin: Int = 0
    var end: Int = 100
    private var middle: Int = 0

    private var isFinalGuess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        begin = intent.getIntExtra("begin", 0)
        end = intent.getIntExtra("end", 100)
        Log.d("mytag", "begin = $begin, end = $end")

        makeNextGuess()
    }

    private fun makeNextGuess() {
        val tvQuestion = findViewById<TextView>(R.id.question)


        if (end - begin <= 1) {
            isFinalGuess = true
            middle = end

            tvQuestion.text = "Is your number $middle?"
        } else {
            isFinalGuess = false
            middle = (begin + end) / 2

            tvQuestion.text = "Is your number > $middle?"
        }
    }

    fun onYesNoClick(view: View) {
        when (view.id) {
            R.id.yes -> {
                Log.d("mytag", "yes")
                if (isFinalGuess) {

                    finishGame(middle)
                    return
                }

                begin = middle + 1
            }
            R.id.no -> {
                Log.d("mytag", "no")
                if (isFinalGuess) {

                    finishGame(begin)
                    return
                }

                end = middle
            }
        }


        if (begin > end) {
            val tvQuestion = findViewById<TextView>(R.id.question)
            tvQuestion.text = "You are lying me! Try again."
            val yes = findViewById<Button>(R.id.yes)
            val no = findViewById<Button>(R.id.no)
            yes.isEnabled = false
            no.isEnabled = false
            return
        }

        makeNextGuess()
    }

    private fun finishGame(number: Int) {
        val tvQuestion = findViewById<TextView>(R.id.question)
        tvQuestion.text = "Your number is $number! I win!"

        val yes = findViewById<Button>(R.id.yes)
        val no = findViewById<Button>(R.id.no)
        yes.isEnabled = false
        no.isEnabled = false
    }
}