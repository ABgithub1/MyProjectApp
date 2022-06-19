package com.example.myprojectapp.data.service.preferences

import android.content.Context
import com.example.myprojectapp.model.settings.Language
import com.example.myprojectapp.model.settings.NightMode
import com.example.myprojectapp.service.LanguageService
import com.example.myprojectapp.service.NightModeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreferencesServiceImpl(context: Context) : NightModeService, LanguageService {

    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override var nightMode: NightMode by enumPref(KEY_NIGHT_MODE, NightMode.SYSTEM)

    override var language: Language by PrefsDelegate(
        sharedPrefs,
        getValue = {
            getString(KEY_LANGUAGE, null)
                ?.let { enumValueOf<Language>(it) }
                ?: Language.EN
        },
        setValue = {
            putString(KEY_LANGUAGE, it.name)
            _languageFlow.tryEmit(it)
        }
    )

    private val _languageFlow = MutableStateFlow(language)
    override val languageFlow: Flow<Language> = _languageFlow.asStateFlow()

    private inline fun <reified E : Enum<E>> enumPref(key: String, defaultValue: E) =
        PrefsDelegate(
            sharedPrefs,
            getValue = { getString(key, null)?.let(::enumValueOf) ?: defaultValue },
            setValue = { putString(key, it.name) }
        )

    companion object {
        private const val PREFS_NAME = "prefs"

        private const val KEY_NIGHT_MODE = "night_mode"
        private const val KEY_LANGUAGE = "language"
    }
}