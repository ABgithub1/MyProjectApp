package com.example.myprojectapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapp.model.LceState
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.usecase.GetTopHeadlinesNewsUseCase
import com.example.myprojectapp.usecase.SaveNewsToDatabaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class TopNewsViewModel(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase,
    private val saveNewsToDatabaseUseCase: SaveNewsToDatabaseUseCase
) :
    ViewModel() {

    private var currentPage = 1
    private var isLoading = false
    private var isThereNewData = true

    private val fetchFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dataFlow = fetchFlow
        .filter { !isLoading }
        .mapLatest {
            isLoading = true
            getTopHeadlinesNewsUseCase(currentPage).fold(
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
        fetchFlow.tryEmit(Unit)
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveNewsToDatabaseUseCase(article)
        }
    }

    init {
        onLoadMore()
    }

}

