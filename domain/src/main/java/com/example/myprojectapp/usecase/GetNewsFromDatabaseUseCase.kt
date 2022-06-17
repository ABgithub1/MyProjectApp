package com.example.myprojectapp.usecase

import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.repository.NewsLocalRepository

class GetNewsFromDatabaseUseCase(private val repository: NewsLocalRepository) {

    suspend operator fun invoke(): Result<List<Article>> {
        return repository.getAllArticlesFromDatabase()
    }

}