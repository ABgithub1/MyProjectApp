package com.example.myprojectapp.repository

import com.example.myprojectapp.model.news.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalRepository {

    suspend fun insertArticleToDatabase(article: Article): Result<Unit>

    suspend fun getAllArticlesFromDatabase(): Result<List<Article>>

    suspend fun deleteArticleFromDatabase(article: Article): Result<Unit>

    suspend fun subscribeToChangesDb(): Result<Flow<List<Article>>>

}