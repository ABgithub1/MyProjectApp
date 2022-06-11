package com.example.myprojectapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myprojectapp.databinding.ItemArticleBinding
import com.example.myprojectapp.model.ArticlePaging
import com.example.myprojectapp.model.news.Article

class ArticlesListAdapter(
    private val longItemClick: (Article) -> Unit,
    private val itemClick: (Article) -> Unit
) : ListAdapter<Article, ArticlesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder(
            binding = ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), longItemClick = longItemClick, itemClick = itemClick
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}