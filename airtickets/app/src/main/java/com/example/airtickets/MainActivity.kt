package com.example.airtickets

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var etDepartDate: EditText
    private lateinit var etReturnDate: EditText

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        etDepartDate = findViewById(R.id.etDepartDate)
        etReturnDate = findViewById(R.id.etReturnDate)

        val cities = resources.getStringArray(R.array.cities)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerTo.setSelection(1)

        etDepartDate.setOnClickListener { showDatePicker(etDepartDate) }
        etReturnDate.setOnClickListener { showDatePicker(etReturnDate) }

        findViewById<EditText>(R.id.etAdults).setText("1")
        findViewById<EditText>(R.id.etChildren).setText("0")
        findViewById<EditText>(R.id.etInfants).setText("0")

        findViewById<android.widget.Button>(R.id.btnSearch).setOnClickListener {
            val from = spinnerFrom.selectedItem?.toString() ?: ""
            val to = spinnerTo.selectedItem?.toString() ?: ""
            val depart = etDepartDate.text.toString()
            val ret = etReturnDate.text.toString()
            Toast.makeText(
                this,
                "$from → $to\n$depart — $ret",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showDatePicker(target: EditText) {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                target.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = System.currentTimeMillis() - 1000
        dialog.show()
    }
}