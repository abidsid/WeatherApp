package com.aliabid.weather.network

import com.aliabid.weather.models.Current
import com.aliabid.weather.models.Location
import com.aliabid.weather.models.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface WeatherApi {
    @GET("current.json")
    suspend fun getWeather(@Query("key") apiKey: String, @Query("q") city: String): WeatherResponse

    @GET("search.json")
    suspend fun search(@Query("key") apiKey: String, @Query("q") query: String): List<Location>
}

class WeatherService @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(city: String): Current {
        return withContext(Dispatchers.IO) {
            val response = api.getWeather("312cb5c33db74a09ae5175628241512", city); //.getWeather()
            response.current
        }
    }

    suspend fun searchData(query: String): List<Location> {
        try {
            val response = api.search("312cb5c33db74a09ae5175628241512", query)
            return response
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }

    suspend fun searchWeatherData(query: String): List<WeatherResponse> {
        try {
            val list = mutableListOf<WeatherResponse>()
            val response = api.search("312cb5c33db74a09ae5175628241512", query)

            response.forEach {
                val weatherResponse = api.getWeather("312cb5c33db74a09ae5175628241512", it.url)

                list.add(weatherResponse)
            }

            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }
}
