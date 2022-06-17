package com.example.myprojectapp.data.database

import androidx.room.*
import com.example.myprojectapp.data.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ArticleDao {
    @Query("SELECT * FROM articleentity")
    suspend fun getAll(): List<ArticleEntity>

    @Query("SELECT * FROM articleentity WHERE id LIKE (:id)")
    suspend fun loadArticleById(id: Long): ArticleEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM articleentity")
    fun subscribeChanges(): Flow<List<ArticleEntity>>

    @Delete
    suspend fun delete(article: ArticleEntity)
}