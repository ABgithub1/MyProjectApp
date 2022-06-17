package com.example.myprojectapp.data.di


import com.example.myprojectapp.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetTopHeadlinesNewsUseCase)
    factoryOf(::GetEverythingNewsUseCase)
    factoryOf(::GetNewsFromDatabaseUseCase)
    factoryOf(::SaveNewsToDatabaseUseCase)
    factoryOf(::SubscribeToChangesDatabaseUseCase)
    factoryOf(::DeleteNewsFromDatabaseUseCase)
}