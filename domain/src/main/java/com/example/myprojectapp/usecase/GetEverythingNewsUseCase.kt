package com.example.myprojectapp.usecase

import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.repository.NewsRemoteRepository

class GetEverythingNewsUseCase(private val repository: NewsRemoteRepository) {

    suspend operator fun invoke(query: String, page: Int): Result<List<Article>> {
        return repository.getEverythingNews(query = query, page = page)
    }

}