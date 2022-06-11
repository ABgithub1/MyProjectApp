package com.example.myprojectapp

import android.app.Application
import com.example.myprojectapp.data.di.allModulesModule
import com.example.myprojectapp.di.allViewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                allModulesModule,
                allViewModelsModule
            )
        }

    }
}