package com.example.myprojectapp.data.di


import com.example.myprojectapp.usecase.GetEverythingNewsUseCase
import com.example.myprojectapp.usecase.GetTopHeadlinesNewsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetTopHeadlinesNewsUseCase)
    factoryOf(::GetEverythingNewsUseCase)
}