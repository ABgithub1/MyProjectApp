package com.example.myprojectapp.data.api

import com.example.myprojectapp.data.constants.Constants.Companion.EVERYTHING_REQUEST
import com.example.myprojectapp.data.constants.Constants.Companion.TOP_HEADLINES_REQUEST
import com.example.myprojectapp.data.model.ArticleDTO
import com.example.myprojectapp.data.model.NewsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET(TOP_HEADLINES_REQUEST)
    suspend fun getTopHeadlinesNews(
        @Query("country")
        countryCode: String = "us",
        @Query("pageSize")
        pageSize: Int = MAXIMUM_PAGE_SIZE,  // The number of results to return per page (request). 20 is the default, 100 is the maximum.
        @Query("page")
        page: Int,
    ): NewsResponseDTO

    @GET(EVERYTHING_REQUEST)
    suspend fun getEverythingNews(
        @Query("q") query: String,
        @Query("pageSize")
        pageSize: Int = MAXIMUM_PAGE_SIZE,
        @Query("page")
        page: Int,
        @Query("language")
        language: String = "ru"
    ): NewsResponseDTO

    companion object {
        const val MAXIMUM_PAGE_SIZE = 20
    }

}

// https://newsapi.org/v2/top-headlines?country=us&apiKey=dbdf332f21b641858d958efd263d3a11&pageSize=20&page=1
// API_KEY_1 = dbdf332f21b641858d958efd263d3a11