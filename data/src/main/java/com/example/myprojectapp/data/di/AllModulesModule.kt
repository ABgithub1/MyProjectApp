package com.example.myprojectapp.data.di

import org.koin.dsl.module

val allModulesModule = module {
    includes(
        networkModule,
        databaseModule,
        repositoryModule,
        useCaseModule,
        serviceModule
    )
}