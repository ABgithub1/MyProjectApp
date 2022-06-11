package com.example.myprojectapp.data.model

data class ArticleDTO(
    val id: Long = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceDTO,
    val title: String,
    val url: String,
    val urlToImage: String
)
