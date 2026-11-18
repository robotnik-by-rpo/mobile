package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.weather.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val apiKey = "4b9e4fd140e714308294fe8adb0ee207"
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.weather = WeatherData("Moscow", 0.0, "Нет данных", 0, 0.0)
        binding.loading = false
    }

    fun onUpdateClick(view: View) {
        val city = binding.cityInput.text.toString().trim()
        if (city.isNotEmpty()) {
            fetchWeather(city)
        }
    }

    private fun fetchWeather(city: String) {
        binding.loading = true
        scope.launch {
            try {
                val weather = withContext(Dispatchers.IO) {
                    val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"
                    val response = URL(url).readText()
                    val json = JSONObject(response)

                    val main = json.getJSONObject("main")
                    val weatherArray = json.getJSONArray("weather")
                    val weatherObj = weatherArray.getJSONObject(0)
                    val wind = json.getJSONObject("wind")

                    WeatherData(
                        cityName = json.getString("name"),
                        temperature = main.getDouble("temp"),
                        description = weatherObj.getString("description"),
                        humidity = main.getInt("humidity"),
                        windSpeed = wind.getDouble("speed")
                    )
                }
                binding.weather = weather
                Log.d("mytag", "Weather updated for $city: ${weather.temperature}")
            } catch (e: Exception) {
                Log.e("mytag", "Error fetching weather", e)
            } finally {
                binding.loading = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}

data class WeatherData(
    val cityName: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
)