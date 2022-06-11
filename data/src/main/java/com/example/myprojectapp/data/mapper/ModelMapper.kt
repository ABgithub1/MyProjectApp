package com.example.myprojectapp.data.mapper

import com.example.myprojectapp.data.model.ArticleDTO
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.model.news.Source

fun ArticleDTO.toDomainModel(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = Source(source.id, source.name),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}