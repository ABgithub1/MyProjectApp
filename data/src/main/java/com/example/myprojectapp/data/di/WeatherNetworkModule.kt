package com.example.myprojectapp.data.di

import com.example.myprojectapp.data.api.weather.WeatherAPI
import com.example.myprojectapp.data.constants.Constants.Companion.BASE_URL_WEATHER
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val weatherNetworkModule = module {

    single(qualifier("weather_api")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>(qualifier("weather_api")).create<WeatherAPI>()
    }

}