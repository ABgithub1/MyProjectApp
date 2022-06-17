package com.example.myprojectapp.usecase

import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.repository.NewsLocalRepository
import kotlinx.coroutines.flow.Flow

class SubscribeToChangesDatabaseUseCase(private val repository: NewsLocalRepository) {

    suspend operator fun invoke(): Result<Flow<List<Article>>> {
        return repository.subscribeToChangesDb()
    }

}