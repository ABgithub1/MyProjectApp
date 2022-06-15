package com.example.myprojectapp.ui.news

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapp.model.LceState
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.usecase.GetTopHeadlinesNewsUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class TopNewsViewModel(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase
) :
    ViewModel() {

//    private val _lceFlow = MutableStateFlow<LceState<List<Article>>>(LceState.Loading)
//    val lceFlow: Flow<LceState<List<Article>>> = _lceFlow.asStateFlow()

    private var currentPage = 1
    private var isLoading = false

    private val fetchFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    suspend fun getList(): Result<List<Article>> {
        return getTopHeadlinesNewsUseCase(1)
    }

    val flow = fetchFlow
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
}

