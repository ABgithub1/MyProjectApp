package com.example.myprojectapp

import android.app.Application
import android.content.Context
import com.example.myprojectapp.data.di.allModulesModule
import com.example.myprojectapp.di.allViewModelsModule
import com.example.myprojectapp.service.LanguageService
import com.example.myprojectapp.service.language.LanguageAwareContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    private val languageService by inject<LanguageService>()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageAwareContext(base, application = this))
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                allModulesModule,
                allViewModelsModule
            )
        }

        languageService
            .languageFlow
            .onEach {
                (baseContext as LanguageAwareContext).appLanguage = it
            }
            .launchIn(CoroutineScope(Dispatchers.Main))

    }
}