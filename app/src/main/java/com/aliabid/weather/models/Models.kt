package com.aliabid.weather.models

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    var id: Long,
    var name: String,
    var region: String,
    var country: String,
    var lat: Double,
    var lon: Double,
    var url: String
)

data class Current(
    val temp_c: Float,
    val condition: Condition,
    val humidity: Int,
    val uv: Float,
    val feelslike_c: Float
)

data class Condition(
    val text: String,
    val icon: String
)
