package com.aliabid.weather.views

import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aliabid.weather.models.Current
import com.aliabid.weather.models.Location
import com.aliabid.weather.models.WeatherResponse
import com.aliabid.weather.network.NetworkMonitor
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
): ViewModel() {
    private val _locations = MutableStateFlow(value = emptyList<WeatherResponse>())
    val locations: StateFlow<List<WeatherResponse>> get() = _locations

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedWeather = MutableStateFlow<WeatherResponse?>(null)
    val selectedWeather: StateFlow<WeatherResponse?> = _selectedWeather

    val isConnected: StateFlow<Boolean> = networkMonitor.isConnected
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), networkMonitor.isConnected.value)


    init {
       // getGames()
        //getProducts()
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
                    val weatherResponseJson = preferences[WEATHER_RESPONSE_KEY] ?: return@map null
                    Gson().fromJson(weatherResponseJson, WeatherResponse::class.java)
                }
                .collect { weatherResponse ->
                    _selectedWeather.value = weatherResponse
                }


//                .filter { it.isNotBlank() }
//                .flatMapLatest { query ->
//                    repository.searchData(query)
//                    // Make your API call here to fetch weather data
//
//                }
//                .collect { results ->
//                    _searchResults.value = results
//                }
        }

        viewModelScope.launch{
            searchQuery
                .debounce(300L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        _locations.value = getLocationUseCase(query)// fetchSearchResults(query)
                    } else {
                        _locations.value = emptyList()
                    }
                }
        }
    }

    private val WEATHER_RESPONSE_KEY = stringPreferencesKey("weather_response")
//
//    val weatherResponseFlow: Flow<WeatherResponse?> = dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                throw exception
//            }
//        }
//        .map { preferences ->
//            val weatherResponseJson = preferences[WEATHER_RESPONSE_KEY] ?: return@map null
//            Gson().fromJson(weatherResponseJson, WeatherResponse::class.java)
//        }

    fun saveWeatherResponse(weatherResponse: WeatherResponse) {
        val weatherResponseJson = Gson().toJson(weatherResponse)
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[WEATHER_RESPONSE_KEY] = weatherResponseJson
            }
        }
    }

    fun selectWeather(weather: WeatherResponse) {
        _selectedWeather.value = weather
        _locations.value = emptyList()// Clear search results
        saveWeatherResponse(weather)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun seachWeather() {
        viewModelScope.launch {
            try {
                val location = getLocationUseCase("chittagong")
                _locations.value = location
            } catch (_: Exception) {

            }
        }
    }

    private fun getGames() {
        viewModelScope.launch {
            try {
                val location = getLocationUseCase("chittagong")
                _locations.value = location
            } catch (_: Exception) {

            }
        }
    }

//    private fun getCurrent() {
//        viewModelScope.launch {
//            try {
//                val current = getCurrentUseCase("chittagong")
//                _current.value = current as Nothing?
//            } catch (_: Exception) {
//
//            }
//        }
//    }

}
//
//@OptIn(ExperimentalCoroutinesApi::class)
//@HiltViewModel
//class WeatherViewModel @Inject constructor(
//    private val repository: WeatherRepository
//) : ViewModel() {
//    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
//    val weatherData: StateFlow<WeatherResponse?> = _weatherData
//
//    fun fetchWeather(city: String) {
//        viewModelScope.launch {
//            repository.getWeatherData(city).collect {
//                _weatherData.value = it
//            }
//        }
//    }
//
//    private val _searchQuery = MutableStateFlow("")
//    val searchQuery: StateFlow<String> = _searchQuery
//
//    private val _searchResults = MutableStateFlow<List<Location>>(emptyList())
//    val searchResults: StateFlow<List<Location>> = _searchResults
//
//    private val _selectedWeather = MutableStateFlow<WeatherResponse?>(null)
//    val selectedWeather: StateFlow<WeatherResponse?> = _selectedWeather
//
//    init {
//        viewModelScope.launch {
//            searchQuery
//                .debounce(300L)
//                .distinctUntilChanged()
//                .collect { query ->
//                    if (query.isNotEmpty()) {
//                       repository.searchData(query) // fetchSearchResults(query)
//                    } else {
//                        _searchResults.value = emptyList()
//                    }
//                }
////                .filter { it.isNotBlank() }
////                .flatMapLatest { query ->
////                    repository.searchData(query)
////                    // Make your API call here to fetch weather data
////
////                }
////                .collect { results ->
////                    _searchResults.value = results
////                }
//        }
//    }
//
//    fun updateSearchQuery(query: String) {
//        _searchQuery.value = query
//    }
//
//    fun selectWeather(weather: WeatherResponse) {
//        _selectedWeather.value = weather
//        _searchResults.value = emptyList()// Clear search results
//    }
//}
