package com.example.myprojectapp.ui.retrofit

import androidx.lifecycle.ViewModel
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.usecase.GetEverythingNewsUseCase
import com.example.myprojectapp.usecase.GetTopHeadlinesNewsUseCase


class RetrofitViewModel(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase,
    private val getEverythingNewsUseCase: GetEverythingNewsUseCase
) :
    ViewModel() {

    suspend fun getTopNews(): Result<List<Article>> {
        return getTopHeadlinesNewsUseCase.invoke(1)
    }

    suspend fun getEverythingNews(): Result<List<Article>> {
        return getEverythingNewsUseCase("a", 1)
    }

}
