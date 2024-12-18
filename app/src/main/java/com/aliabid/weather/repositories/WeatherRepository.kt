package com.aliabid.weather.repositories

import com.aliabid.weather.models.Current
import com.aliabid.weather.models.Location
import com.aliabid.weather.models.WeatherResponse
import com.aliabid.weather.network.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {
    suspend fun getWeather(city: String): Current {
        return weatherService.getWeather(city)
    }

    suspend fun searchData(query: String): List<Location> {
        return withContext(Dispatchers.IO) {
            weatherService.searchData(query)
        }
    }

    suspend fun searchWeatherData(query: String): List<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            weatherService.searchWeatherData(query)
        }
    }
}
