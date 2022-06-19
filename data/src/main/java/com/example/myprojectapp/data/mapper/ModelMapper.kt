package com.example.myprojectapp.data.mapper

import com.example.myprojectapp.data.model.ArticleDTO
import com.example.myprojectapp.data.model.ArticleEntity
import com.example.myprojectapp.data.model.SourceDTO
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.model.news.Source

fun ArticleDTO.toDomainModel(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.toDomainModel(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleEntity.toDomainModel(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = Source(id = "", name = source),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Article.toEntityModel(): ArticleEntity {
    return ArticleEntity(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.name,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceDTO.toDomainModel(): Source {
    return Source(
        id = id,
        name = name
    )
}