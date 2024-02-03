package com.example.thenews.news.newsList

import com.example.thenews.model.presentation.New

interface NewsListAction {
    object InitScreen : NewsListAction
    data class SearchNews(
        val searchQuery: String
    ) : NewsListAction

    data class OnClickFavourite(
        val favouriteNew: New
    )
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
