package com.example.myprojectapp.model.settings

import java.util.*

enum class Language(
    val locale: Locale
) {
    EN(Locale.ENGLISH),
    RU(Locale("ru"))
}