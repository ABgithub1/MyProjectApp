//package com.example.myprojectapp.paging
//
//import com.example.myprojectapp.model.news.Article
//import com.example.myprojectapp.usecase.GetTopHeadlinesNewsUseCase
//import kotlinx.coroutines.channels.BufferOverflow
//import kotlinx.coroutines.flow.*
//
//class PagingSource(
//    private val getTopHeadlinesNewsUseCase: GetTopHeadlinesNewsUseCase
//) {
//    private val loadMoreFlow = MutableSharedFlow<LoadState>(
//        replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//
//    private var isLoading = false
//    private var currentPage = 0
//
//    fun onLoadMore() {
//        loadMoreFlow.tryEmit(LoadState.LOAD_MORE)
//    }
//
//    fun onRefresh() {
//        loadMoreFlow.tryEmit(LoadState.REFRESH)
//    }
//
//    val
//
//    enum class LoadState {
//        LOAD_MORE, REFRESH
//    }
//}
