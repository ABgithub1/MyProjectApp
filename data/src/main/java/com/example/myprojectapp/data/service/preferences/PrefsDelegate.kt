package com.example.myprojectapp.data.service.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

internal class PrefsDelegate<T>(
    private val sharedPrefs: SharedPreferences,
    private val getValue: SharedPreferences.() -> T,
    private val setValue: SharedPreferences.Editor.(T) -> Unit
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return sharedPrefs.getValue()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        sharedPrefs.edit { setValue(value) }
    }
}