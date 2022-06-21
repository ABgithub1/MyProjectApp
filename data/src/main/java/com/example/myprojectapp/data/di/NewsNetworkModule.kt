package com.example.myprojectapp.data.di

import com.example.myprojectapp.data.api.news.AuthInterceptor
import com.example.myprojectapp.data.api.news.NewsAPI
import com.example.myprojectapp.data.api.weather.WeatherAPI
import com.example.myprojectapp.data.constants.Constants.Companion.API_KEY_3
import com.example.myprojectapp.data.constants.Constants.Companion.BASE_URL
import com.example.myprojectapp.data.constants.Constants.Companion.BASE_URL_WEATHER
import com.example.myprojectapp.data.constants.Constants.Companion.WEATHER_API_KEY_1
import okhttp3.OkHttpClient
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val networkModule = module {

    single() {
        OkHttpClient
            .Builder()
            .build()
    }

    single(qualifier("news_api")) {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(API_KEY_3))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    single {
        get<Retrofit>(qualifier("news_api")).create<NewsAPI>()
    }

}
