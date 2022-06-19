package com.example.myprojectapp.data.di

import com.example.myprojectapp.data.service.preferences.PreferencesServiceImpl
import com.example.myprojectapp.service.LanguageService
import com.example.myprojectapp.service.NightModeService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val serviceModule = module {
    singleOf(::PreferencesServiceImpl){
        bind<NightModeService>()
        bind<LanguageService>()
    }
}