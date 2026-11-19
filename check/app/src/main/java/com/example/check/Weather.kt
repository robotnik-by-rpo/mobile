package com.example.check

data class Weather(
    val city: String,
    val temperatureC: Double,
    val windDirection: String,
    val windSpeed: Double,
    val description: String
)

data class OwmResponse(
    val name: String,
    val main: OwmMain,
    val weather: List<OwmWeather>,
    val wind: OwmWind
)

data class OwmMain(val temp: Double)
data class OwmWeather(val description: String)
data class OwmWind(val speed: Double, val deg: Int)

data class WeatherList(val weather: List<Weather>)