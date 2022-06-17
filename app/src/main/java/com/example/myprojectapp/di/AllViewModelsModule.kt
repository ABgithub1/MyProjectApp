package com.example.myprojectapp.di

import com.example.myprojectapp.ui.home.HomeViewModel
import com.example.myprojectapp.ui.news.SavedNewsViewModel
import com.example.myprojectapp.ui.news.TopNewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val allViewModelsModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::TopNewsViewModel)
    viewModelOf(::SavedNewsViewModel)
}