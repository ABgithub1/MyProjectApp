package com.example.myprojectapp.data.repository

import com.example.myprojectapp.data.database.ArticleDao
import com.example.myprojectapp.data.mapper.toDomainModel
import com.example.myprojectapp.data.mapper.toEntityModel
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.repository.NewsLocalRepository
import kotlinx.coroutines.flow.map

internal class LocalRepositoryImpl(private val dao: ArticleDao) : NewsLocalRepository {

    override suspend fun insertArticleToDatabase(article: Article) = runCatching {
        dao.insertArticle(article = article.toEntityModel())
    }

    override suspend fun getAllArticlesFromDatabase() = runCatching {
        dao.getAll()
    }.map {
        it.map { articleEntity ->
            articleEntity.toDomainModel()
        }
    }

    override suspend fun deleteArticleFromDatabase(article: Article) = runCatching {
        dao.delete(article.toEntityModel())
    }

    override suspend fun subscribeToChangesDb() = runCatching {
        dao.subscribeChanges().map {
            it.map { articleEntity ->
                articleEntity.toDomainModel()
            }
        }
    }

}