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

    private var isLoading = false
    private var currentPage = 1

    private val loadMoreFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val queryFlow = MutableStateFlow("")

    val dataFlow = queryFlow
        .debounce(1000)
        .onEach {
            currentPage = 1
        }.flatMapLatest { query ->
            newsDataFlow(query)
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )


    fun onLoadMore() {
        loadMoreFlow.tryEmit(Unit)
    }

    fun onQueryTextChanged(query: String) {
        queryFlow.value = query
    }


    fun saveArticle(article: Article) {
        viewModelScope.launch {
            saveNewsToDatabaseUseCase(article).onSuccess {
            }
        }
    }

    private fun newsDataFlow(query: String): Flow<LceState<List<Article>>> {
        return loadMoreFlow
            .filter { !isLoading }
            .onEach { isLoading = true }
            .mapLatest {
                getEverythingNewsUseCase(query, currentPage).fold(
                    onSuccess = {
                        currentPage++
                        it
                    },
                    onFailure = {
                        it.message
                        emptyList()
                    }
                )
            }.runningReduce { accumulator, value -> accumulator + value }.map {
                LceState.Content(it)
            }
            .onEach { isLoading = false }
    }

    init {
        onLoadMore()
    }

}