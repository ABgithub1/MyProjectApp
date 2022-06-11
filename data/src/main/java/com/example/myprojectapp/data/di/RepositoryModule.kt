package com.example.myprojectapp.data.di


import com.example.myprojectapp.data.repository.RemoteRepositoryImpl
import com.example.myprojectapp.repository.NewsRemoteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {

    singleOf(::RemoteRepositoryImpl) {
        bind<NewsRemoteRepository>()
    }

}