package com.example.myprojectapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprojectapp.model.LceState
import com.example.myprojectapp.model.news.Article
import com.example.myprojectapp.usecase.DeleteNewsFromDatabaseUseCase
import com.example.myprojectapp.usecase.GetNewsFromDatabaseUseCase
import com.example.myprojectapp.usecase.SubscribeToChangesDatabaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedNewsViewModel(
    private val subscribeToChangesDatabaseUseCase: SubscribeToChangesDatabaseUseCase,
    private val deleteNewsFromDatabaseUseCase: DeleteNewsFromDatabaseUseCase
) : ViewModel() {

    private val _lceDatabaseFlow = MutableStateFlow<LceState<Flow<List<Article>>>>(LceState.Loading)
    val lceDatabaseFlow: Flow<LceState<Flow<List<Article>>>> = _lceDatabaseFlow.asStateFlow()

    init {
        viewModelScope.launch {
            subscribeToChangesDatabaseUseCase.invoke().fold(
                onSuccess = {
                    _lceDatabaseFlow.tryEmit(LceState.Content(it))
                },
                onFailure = {
                    emptyList<Article>()
                }
            )
        }
    }

    fun onArticleSwiped(article: Article) = viewModelScope.launch {
        deleteNewsFromDatabaseUseCase(article).onSuccess {

        }
    }

}