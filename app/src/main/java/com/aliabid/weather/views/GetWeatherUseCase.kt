package com.aliabid.weather.views

import com.aliabid.weather.models.Current
import com.aliabid.weather.models.WeatherResponse
import com.aliabid.weather.repositories.WeatherRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(query: String): List<WeatherResponse> {
        return weatherRepository.searchWeatherData(query)
    }
}

class GetCurrentUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Current {
        return weatherRepository.getWeather(city)
    }
}