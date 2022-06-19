package com.example.myprojectapp.service.language

import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import com.example.myprojectapp.extentions.applySelectedAppLanguage
import com.example.myprojectapp.model.settings.Language
import java.util.*

class LanguageAwareContext(base: Context, application: Application) : ContextWrapper(base) {

    var appLanguage: Language = getDefaultLanguage()
        set(value) {
            field = value
            localizedResources = getLocalizedResources(value)
        }

    private var localizedResources = getLocalizedResources(appLanguage)

    init {
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                localizedResources = getLocalizedResources(appLanguage)
            }

            override fun onLowMemory() { }
        })
    }

    override fun getResources(): Resources = localizedResources

    private fun getLocalizedResources(language: Language): Resources {
        return baseContext.applySelectedAppLanguage(language).resources
    }

    private fun getDefaultLanguage(): Language {
        return Language.values()
            .firstOrNull { language ->
                language.locale.language == Locale.getDefault().language
            }
            ?: DEFAULT_LANGUAGE
    }

    companion object {
        private val DEFAULT_LANGUAGE = Language.EN
    }
}