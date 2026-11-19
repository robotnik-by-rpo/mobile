package com.example.check

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.check.OwmResponse
import com.example.check.Weather
import com.example.check.WeatherList
import com.example.check.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var weatherList: List<Weather> = emptyList()
    private var currentWeather: Weather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherList = loadWeatherFromJson()

        val cities = resources.getStringArray(R.array.cities)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.citySpinner.adapter = adapter

        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentWeather = weatherList.getOrNull(position)
                render()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.scaleGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int -> render() }

        binding.cbWind.setOnCheckedChangeListener { _, _ -> render() }

        binding.btnRefresh.setOnClickListener {
            loadFromApi(cities[binding.citySpinner.selectedItemPosition])
        }
    }

    private fun loadWeatherFromJson(): List<Weather> {
        return try {
            val gson = Gson()
            val stream = resources.openRawResource(R.raw.weather)
            val reader = InputStreamReader(stream)
            val data: WeatherList = gson.fromJson(reader, WeatherList::class.java)
            Log.d("mytag", "weather loaded: ${data.weather.size}")
            data.weather
        } catch (e: Exception) {
            Log.e("mytag", "Ошибка загрузки JSON: ${e.message}")
            emptyList()
        }
    }

    private fun render() {
        val w = currentWeather ?: return
        val celsius = binding.rbCelsius.isChecked

        val temp = if (celsius) w.temperatureC
        else w.temperatureC * 9 / 5 + 32
        val unit = if (celsius) "°C" else "°F"

        binding.tvTemperature.text = String.format("%+.1f %s", temp, unit)
        binding.tvDescription.text = w.description

        binding.tvWind.visibility = if (binding.cbWind.isChecked) View.VISIBLE else View.GONE
        binding.tvWind.text = "Ветер: ${w.windDirection}, ${w.windSpeed} м/с"
    }

    private fun loadFromApi(city: String) {
        val apiKey = "4b9e4fd140e714308294fe8adb0ee207"
        val url = "https://api.openweathermap.org/data/2.5/weather" +
                "?q=$city&appid=$apiKey&units=metric&lang=ru"

        lifecycleScope.launch {
            try {
                val json = withContext(Dispatchers.IO) { httpGet(url) }
                val gson = Gson()
                val resp = gson.fromJson(json, OwmResponse::class.java)
                val direction = degToDirection(resp.wind.deg)
                currentWeather = Weather(
                    city = resp.name,
                    temperatureC = resp.main.temp,
                    windDirection = direction,
                    windSpeed = resp.wind.speed,
                    description = resp.weather.firstOrNull()?.description ?: ""
                )
                render()
            } catch (e: Exception) {
                Log.e("mytag", "Ошибка API: ${e.message}")
            }
        }
    }

    private fun httpGet(urlStr: String): String {
        val conn = URL(urlStr).openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        conn.readTimeout = 5000
        return conn.inputStream.bufferedReader().use { it.readText() }
    }

    private fun degToDirection(deg: Int): String {
        val dirs = listOf("С", "СВ", "В", "ЮВ", "Ю", "ЮЗ", "З", "СЗ")
        return dirs[((deg + 22.5) / 45).toInt() % 8]
    }
}