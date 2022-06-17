package com.example.myprojectapp.usecase

import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.repository.NewsLocalRepository

class SaveNewsToDatabaseUseCase(private val repository: NewsLocalRepository) {

    suspend operator fun invoke(article: Article): Result<Unit> {
        return repository.insertArticleToDatabase(article)
    }

}