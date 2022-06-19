package com.example.myprojectapp.ui.settings.nightmode

import androidx.lifecycle.ViewModel
import com.example.myprojectapp.service.NightModeService

class NightModeViewModel(private val prefsService: NightModeService) : ViewModel() {

    var selectedNightMode by prefsService::nightMode
}