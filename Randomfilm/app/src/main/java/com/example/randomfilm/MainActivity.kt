package com.example.randomfilm
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvCounter: TextView
    private lateinit var btnShow: Button
    private lateinit var btnReset: Button

    private val remaining = mutableListOf<String>()

    private var total = 0

    private var shown = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult  = findViewById(R.id.tvResult)
        tvCounter = findViewById(R.id.tvCounter)
        btnShow   = findViewById(R.id.btnShow)
        btnReset  = findViewById(R.id.btnReset)

        initMovies()

        btnShow.setOnClickListener { showNextMovie() }
        btnReset.setOnClickListener { reset() }
    }

    private fun initMovies() {
        val movies = resources.getStringArray(R.array.movies)
        total = movies.size

        remaining.clear()
        remaining.addAll(movies)
        remaining.shuffle()

        shown = 0
        updateCounter()
    }

    private fun showNextMovie() {
        if (remaining.isEmpty()) {
            tvResult.text = ""
            Toast.makeText(this, R.string.msg_empty, Toast.LENGTH_SHORT).show()
            return
        }

        val movie = remaining.removeAt(0)
        shown++
        tvResult.text = movie
        updateCounter()
    }

    private fun reset() {
        tvResult.text = ""
        tvResult.setHint(R.string.hint_result)
        initMovies()
    }

    private fun updateCounter() {
        tvCounter.text = getString(R.string.msg_counter, shown, total)
    }
}