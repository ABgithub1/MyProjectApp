package com.example.myprojectapp.service

import com.example.myprojectapp.model.settings.Language
import kotlinx.coroutines.flow.Flow

interface LanguageService {

    var language: Language
    val languageFlow: Flow<Language>
}