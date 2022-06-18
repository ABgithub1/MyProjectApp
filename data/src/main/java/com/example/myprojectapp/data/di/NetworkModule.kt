package com.example.myprojectapp.data.di

import com.example.myprojectapp.data.api.AuthInterceptor
import com.example.myprojectapp.data.api.NewsAPI
import com.example.myprojectapp.data.constants.Constants.Companion.API_KEY_1
import com.example.myprojectapp.data.constants.Constants.Companion.API_KEY_2
import com.example.myprojectapp.data.constants.Constants.Companion.API_KEY_3
import com.example.myprojectapp.data.constants.Constants.Companion.API_KEY_4
import com.example.myprojectapp.data.constants.Constants.Companion.API_KEY_6
import com.example.myprojectapp.data.constants.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal val networkModule = module {
    single {
        OkHttpClient
            .Builder()
            .build()
    }

    single {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(API_KEY_2))
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    single {
        get<Retrofit>().create<NewsAPI>()
    }

}
