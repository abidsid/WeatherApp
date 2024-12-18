package com.aliabid.weather.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.aliabid.weather.network.NetworkMonitor
import com.aliabid.weather.network.WeatherApi
import com.aliabid.weather.network.WeatherService
import com.aliabid.weather.repositories.WeatherRepository
import com.aliabid.weather.views.GetCurrentUseCase
import com.aliabid.weather.views.GetLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {

    private const val WEATHER_PREFERENCES_NAME = "weather_preferences"

    @Module
    @InstallIn(SingletonComponent::class)
    object DataStoreModule {

        private val Context.dataStore by preferencesDataStore(name = WEATHER_PREFERENCES_NAME)

        @Provides
        @Singleton
        fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return context.dataStore
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkMonitorModule {
        @Singleton
        @Provides
        fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
            return NetworkMonitor(context)
        }
    }

    @Provides
    @ViewModelScoped
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherService(weatherApi: WeatherApi): WeatherService {
        return WeatherService(weatherApi)
    }

    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepository(weatherService)
    }

    @Provides
    @ViewModelScoped
    fun provideLocationUseCase(weatherRepository: WeatherRepository): GetLocationUseCase {
        return GetLocationUseCase(weatherRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCurrentUseCase(weatherRepository: WeatherRepository): GetCurrentUseCase {
        return GetCurrentUseCase(weatherRepository)
    }
}
