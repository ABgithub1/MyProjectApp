package com.example.myprojectapp.repository

import com.example.myprojectapp.model.news.Article

interface NewsRemoteRepository {

    suspend fun getTopHeadlinesNews(page: Int): Result<List<Article>>

    suspend fun getEverythingNews(query: String, page: Int): Result<List<Article>>

}