package com.example.album

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        private const val PREFS_NAME = "album_prefs"
        private const val KEY_INDEX = "picture_index"
        private const val KEY_SPINNER = "spinner_position"

        private val pictureRes = intArrayOf(
            R.drawable.car1,
            R.drawable.car2,
            R.drawable.car3
        )
    }

    private lateinit var adapter: ArrayAdapter<CharSequence>
    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner
    private lateinit var prefs: SharedPreferences

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        imageView = findViewById(R.id.picture)
        spinner = findViewById(R.id.pictures_list)

        adapter = ArrayAdapter.createFromResource(this, R.array.pictures, R.layout.items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        val savedIndex = prefs.getInt(KEY_INDEX, 0)
        currentIndex = if (savedIndex in pictureRes.indices) savedIndex else 0

        imageView.setImageResource(pictureRes[currentIndex])

        spinner.setSelection(currentIndex, false)
    }

    fun onChangePictureClick(v: View) {
        currentIndex = (currentIndex + 1) % pictureRes.size
        imageView.setImageResource(pictureRes[currentIndex])
        saveState(currentIndex)
        spinner.setSelection(currentIndex, true)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != currentIndex) {
            currentIndex = position
            imageView.setImageResource(pictureRes[currentIndex])
            saveState(currentIndex)
            Toast.makeText(this, "выбран элемент $position", Toast.LENGTH_SHORT).show()
        } else {
            imageView.setImageResource(pictureRes[currentIndex])
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "не выбран элемент", Toast.LENGTH_SHORT).show()
    }

    private fun saveState(index: Int) {
        prefs.edit()
            .putInt(KEY_INDEX, index)
            .putInt(KEY_SPINNER, index)
            .apply()
    }
}