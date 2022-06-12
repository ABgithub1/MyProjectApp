package com.example.myprojectapp.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myprojectapp.databinding.ItemArticleBinding
import com.example.myprojectapp.model.news.Article

class ArticlesViewHolder(
    private val binding: ItemArticleBinding,
    private val itemClick: (Article) -> Unit,
    private val longItemClick: (Article) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article) {
        binding.date.text = article.publishedAt
        binding.title.text = article.title
        binding.source.text = article.source?.name

        if (article.urlToImage != null) {
            binding.image.load(article.urlToImage)
        } else {
            binding.image.load("https://w7.pngwing.com/pngs/150/410/png-transparent-newspaper-computer-icons-journalist-news-corporation-business-text-people-logo.png")
        }

        binding.root.setOnClickListener {
            itemClick(article)
        }

        binding.root.setOnLongClickListener {
            longItemClick(article)
            true
        }

    }
}
