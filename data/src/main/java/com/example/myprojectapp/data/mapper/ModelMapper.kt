package com.example.myprojectapp.data.mapper

import com.example.myprojectapp.data.model.ArticleDTO
import com.example.myprojectapp.data.model.SourceDTO
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.model.news.Source

fun ArticleDTO.toDomainModel(): Article {
    return Article(
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

fun SourceDTO.toDomainModel(): Source {
    return Source(
        id = id,
        name = name
    )
}