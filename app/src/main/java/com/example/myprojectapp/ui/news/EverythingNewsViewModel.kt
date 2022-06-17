package com.example.myprojectapp.ui.news

import androidx.lifecycle.ViewModel
import com.example.myprojectapp.usecase.GetEverythingNewsUseCase

class EverythingNewsViewModel(
    private val getEverythingNewsUseCase: GetEverythingNewsUseCase
) : ViewModel() {

}