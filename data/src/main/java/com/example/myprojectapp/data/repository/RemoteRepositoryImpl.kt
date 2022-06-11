package com.example.myprojectapp.data.repository

import com.example.myprojectapp.data.api.NewsAPI
import com.example.myprojectapp.data.mapper.toDomainModel
import com.example.myprojectapp.repository.NewsRemoteRepository

class RemoteRepositoryImpl(private val api: NewsAPI) : NewsRemoteRepository {

    override suspend fun getTopHeadlinesNews(page: Int) = runCatching {
        api.getTopHeadlinesNews(page = page)
    }.map { article ->
        article.map {
            it.toDomainModel()
        }
    }

    // ToDomainModel

    override suspend fun getEverythingNews(query: String, page: Int) = runCatching {
        api.getEverythingNews(query = query, page = page)
    }.map { article ->
        article.map {
            it.toDomainModel()
        }
    }
}