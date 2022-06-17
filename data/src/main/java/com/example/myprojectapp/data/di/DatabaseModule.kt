package com.example.myprojectapp.data.di

import androidx.room.Room
import com.example.myprojectapp.data.database.ArticleDatabase
import org.koin.dsl.module

internal val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            ArticleDatabase::class.java,
            "articles_db_1"
        )
            .build()
    }

    single {
        get<ArticleDatabase>().personDao()
    }
}