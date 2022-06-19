package com.example.myprojectapp.ui.settings.language

import androidx.lifecycle.ViewModel
import com.example.myprojectapp.model.settings.Language
import com.example.myprojectapp.service.LanguageService

class LanguageViewModel(private val languageService: LanguageService) : ViewModel() {

    var selectedLanguage: Language by languageService::language
}