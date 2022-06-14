package com.example.myprojectapp.ui.retrofit

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapp.model.LceState
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.usecase.GetEverythingNewsUseCase
import com.example.myprojectapp.usecase.GetTopHeadlinesNewsUseCase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class RetrofitViewModel(
    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase,
    private val getEverythingNewsUseCase: GetEverythingNewsUseCase
) :
    ViewModel() {

    private val _lceFlow = MutableStateFlow<LceState<List<Article>>>(LceState.Loading)
    val lceFlow: Flow<LceState<List<Article>>> = _lceFlow.asStateFlow()

    private val fetchingFlow = MutableSharedFlow<LceState<List<Article>>>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var currentPage = 0

    init {
        viewModelScope.launch {
            getTopHeadlinesNewsUseCase(currentPage).onSuccess {
                currentPage++
                _lceFlow.tryEmit(LceState.Content(articles = it))
            }
        }
    }

    fun onLoadMore() {
        fetchingFlow.tryEmit(LceState.Loading)
    }

}

//    suspend fun getTopHeadlinesNews(): Result<List<Article>> {
//        return getTopHeadlinesNewsUseCase.invoke(topNewsPage)
//    }


