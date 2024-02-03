package com.example.thenews.newsList

import com.example.thenews.model.presentation.New

interface NewsListAction {
    object InitScreen : NewsListAction
    data class SearchNews(
        val searchQuery: String
    ) : NewsListAction
}

interface NewsListState {
    object Loading : NewsListState
    data class Success(
        val newsList: List<New>
    ) : NewsListState

    data class Error(
        val errorMessage: String
    ) : NewsListState
}
