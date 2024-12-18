package com.aliabid.weather.views

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliabid.weather.models.WeatherResponse
import com.aliabid.weather.network.NetworkMonitor
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getCurrentUseCase: GetCurrentUseCase,
    private val dataStore: DataStore<Preferences>,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {
    private val _locations = MutableStateFlow(value = emptyList<WeatherResponse>())
    val locations: StateFlow<List<WeatherResponse>> get() = _locations

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedWeather = MutableStateFlow<WeatherResponse?>(null)
    val selectedWeather: StateFlow<WeatherResponse?> = _selectedWeather

    val isConnected: StateFlow<Boolean> = networkMonitor.isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            networkMonitor.isConnected.value
        )

    init {
        viewModelScope.launch {
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    val weatherResponseJson = preferences[weatherResponseKey] ?: return@map null
                    Gson().fromJson(weatherResponseJson, WeatherResponse::class.java)
                }
                .collect { weatherResponse ->
                    _selectedWeather.value = weatherResponse
                }
        }

        viewModelScope.launch {
            searchQuery
                .debounce(300L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        _locations.value = getLocationUseCase(query)
                    } else {
                        _locations.value = emptyList()
                    }
                }
        }
    }

    private val weatherResponseKey = stringPreferencesKey("weather_response")

    fun saveWeatherResponse(weatherResponse: WeatherResponse) {
        val weatherResponseJson = Gson().toJson(weatherResponse)
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[weatherResponseKey] = weatherResponseJson
            }
        }
    }

    fun selectWeather(weather: WeatherResponse) {
        _selectedWeather.value = weather
        _locations.value = emptyList()
        saveWeatherResponse(weather)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun seachWeather() {
        viewModelScope.launch {
            try {
                val location = getLocationUseCase("herndon")
                _locations.value = location
            } catch (_: Exception) {
            }
        }
    }

    private fun getGames() {
        viewModelScope.launch {
            try {
                val location = getLocationUseCase("herndon")
                _locations.value = location
            } catch (_: Exception) {
            }
        }
    }
}
