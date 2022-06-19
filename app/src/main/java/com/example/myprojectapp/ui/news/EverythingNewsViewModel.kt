package com.example.myprojectapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapp.model.LceState
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.usecase.GetEverythingNewsUseCase
import com.example.myprojectapp.usecase.SaveNewsToDatabaseUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EverythingNewsViewModel(
    private val getEverythingNewsUseCase: GetEverythingNewsUseCase,
    private val saveNewsToDatabaseUseCase: SaveNewsToDatabaseUseCase
) : ViewModel() {

    private val fetchFlow = MutableSharedFlow<String>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var searchQuery = ""

    private var currentPage = 1
    private var isLoading = false

    val dataFlow = fetchFlow
        .filter { !isLoading }
        .mapLatest {
            isLoading = true
            getEverythingNewsUseCase(it, currentPage).fold(
                onSuccess = {
                    currentPage++
                    it
                },
                onFailure = {
                    it.message
                    emptyList()
                }
            )
        }.onEach {
            isLoading = false
        }
        .runningReduce { accumulator, value -> accumulator + value }.map {
            LceState.Content(it)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, LceState.Loading)

    fun onLoadMore() {
        fetchFlow.tryEmit(searchQuery)
    }

    fun onQueryTextChanged(query: String) {
        currentPage = 1
        searchQuery = query
        fetchFlow.tryEmit(searchQuery)
    }


    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveNewsToDatabaseUseCase(article).onSuccess {
            }
        }
    }

    init {
        onLoadMore()
    }
}